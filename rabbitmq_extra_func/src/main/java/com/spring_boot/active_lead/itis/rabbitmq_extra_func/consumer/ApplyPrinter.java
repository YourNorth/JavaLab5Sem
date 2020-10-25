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
public class ApplyPrinter {
    private final static String APPLY = "doc.agreement.apply";
    private final static String DOC_EXCHANGE = "doc_exchange";

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
            channel.queueBind(APPLY, DOC_EXCHANGE, APPLY);
            channel.queueBind(APPLY, FANOUT_EXCHANGE, "");
            channel.basicConsume(APPLY, true, (consumerTag, message) -> {
                //Чтение данных о пользователе из Json
                Person person = new ObjectMapper().readValue(message.getBody(), Person.class);
                log.info("ApplyPrinter: Person is {}" + person.toString());
                new CustomPdfWriterImpl().createApplyAgreement("apply agreement " + UUID.randomUUID(), person);
                System.out.println("ApplyPrinter: document is ready");
            }, consumerTag -> {
            });
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
