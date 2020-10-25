package com.spring_boot.active_lead.itis.rabbitmq_extra_func.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.model.Person;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.service.CustomPdfWriterImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
public class AdvancePrinter {
    private final static String ADVANCE = "doc.statement.advance";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            // сколько неподтвержденных задач может брать на себя Consumer
            channel.basicQos(3);
            channel.basicConsume(ADVANCE, true, (consumerTag, message) -> {
                //Чтение данных о пользователе из Json
                Person person = new ObjectMapper().readValue(message.getBody(), Person.class);
                log.info("AdvancePrinter: Person is {}" + person.toString());
                new CustomPdfWriterImpl().createAdvanceStatement("advance statement", person);
                System.out.println("AdvancePrinter: document is ready");
            }, consumerTag -> {
            });
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
