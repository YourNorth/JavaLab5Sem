package com.spring_boot.active_lead.itis.rabbitmq_extra_func.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.model.Person;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.service.CustomPdfWriterImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Slf4j
public class ExtraWorkPrinter {
    private final static String EXTRA_WORK = "doc.agreement.extraWork";
    private static final String DOC_EXCHANGE = "doc_exchange";

    private static final String FANOUT_EXCHANGE = "exchange_fanout";
    private static final String EXCHANGE_TYPE = "fanout";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            // сколько неподтвержденных задач может брать на себя Consumer
            channel.basicQos(3);
            channel.queueBind(EXTRA_WORK, DOC_EXCHANGE, EXTRA_WORK);
            channel.queueBind(EXTRA_WORK, FANOUT_EXCHANGE, "");
            channel.basicConsume(EXTRA_WORK, true, (consumerTag, message) -> {
                //Чтение данных о пользователе из Json
                Person person = new ObjectMapper().readValue(message.getBody(), Person.class);
                log.info("ExtraWorkPrinter: Person is {}" + person.toString());
                new CustomPdfWriterImpl().createExtraWorkAgreement("extra work agreement " + UUID.randomUUID(), person);
                System.out.println("ExtraWorkPrinter: document is ready");
            }, consumerTag -> {
            });
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
