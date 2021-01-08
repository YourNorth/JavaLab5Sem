package com.spring_boot.active_lead.itis.rabbitmq_extra_func.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.model.Person;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.service.CustomPdfWriterImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class AdvancePrinter {

    private static final String FANOUT_EXCHANGE = "exchange_fanout";
    private static final String EXCHANGE_TYPE = "fanout";

    private final static String ADVANCE = "doc.statement.advance";
    private static final String DOC_EXCHANGE = "doc_exchange";

    public AdvancePrinter() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            // сколько неподтвержденных задач может брать на себя Consumer
            channel.basicQos(3);
            //привязываем к topic exchange
            channel.queueBind(ADVANCE, DOC_EXCHANGE, ADVANCE);
            channel.queueBind(ADVANCE, FANOUT_EXCHANGE, "");
            channel.basicConsume(ADVANCE, true, (consumerTag, message) -> {
                //Чтение данных о пользователе из Json
                Person person = new ObjectMapper().readValue(message.getBody(), Person.class);
                log.info("AdvancePrinter: Person is {}" + person.toString());
                new CustomPdfWriterImpl().createAdvanceStatement("advance statement "+ UUID.randomUUID(), person);
                System.out.println("AdvancePrinter: document is ready");
            }, consumerTag -> {
            });
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
