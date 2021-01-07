package com.spring_boot.active_lead.itis.rabbitmq_extra_func.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.model.Person;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

@Service
public class InfoProducerTopic {
    private final static String APPLY = "doc.agreement.apply";
    private final static String PERSONAL_DATE = "doc.agreement.personalDate";
    private final static String EXTRA_WORK = "doc.agreement.extraWork";
    private final static String ADVANCE = "doc.statement.advance";

    private final static String DOC_EXCHANGE = "doc_exchange";
    private final static String EXCHANGE_TYPE = "topic";

    private Channel channel;
    
    public InfoProducerTopic() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(DOC_EXCHANGE, EXCHANGE_TYPE);
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void createAgreements(Person person) {

        try {
            channel.basicPublish(DOC_EXCHANGE, "doc.agreement.apply", null, person.toJson().getBytes());
            channel.basicPublish(DOC_EXCHANGE, "doc.agreement.personalDate", null, person.toJson().getBytes());
            channel.basicPublish(DOC_EXCHANGE, "doc.agreement.extraWork", null, person.toJson().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createStatements(Person person) {
        try {
            channel.basicPublish(DOC_EXCHANGE, "doc.statement.advance", null, person.toJson().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
