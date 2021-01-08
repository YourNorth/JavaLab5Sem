package com.spring_boot.active_lead.itis.rabbitmq_extra_func.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.model.Person;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.service.CustomPdfWriterImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class PersonalDatePrinter {
    private final static String PERSONAL_DATE = "doc.agreement.personalDate";
    private static final String DOC_EXCHANGE = "doc_exchange";

    private static final String FANOUT_EXCHANGE = "exchange_fanout";
    private static final String EXCHANGE_TYPE = "fanout";

    public PersonalDatePrinter() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            // сколько неподтвержденных задач может брать на себя Consumer
            channel.basicQos(3);
            channel.queueBind(PERSONAL_DATE, DOC_EXCHANGE, PERSONAL_DATE);
            channel.queueBind(PERSONAL_DATE, FANOUT_EXCHANGE, "");
            channel.basicConsume(PERSONAL_DATE, true, (consumerTag, message) -> {
                //Чтение данных о пользователе из Json
                Person person = new ObjectMapper().readValue(message.getBody(), Person.class);
                log.info("PersonalDatePrinter: Person is {}" + person.toString());
                new CustomPdfWriterImpl().createPersonalDateAgreement("personal date agreement " + UUID.randomUUID(), person);
                System.out.println("PersonalDatePrinter: document is ready");
            }, consumerTag -> {
            });
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}