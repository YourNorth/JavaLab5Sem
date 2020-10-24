package com.spring_boot.active_lead.itis.rabbitmq_extra_func.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.model.Person;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.concurrent.TimeoutException;

public class InfoProducer {

    private final static String QUEUE_DOC_AGREEMENT1 = "doc.agreement.personalDate";
    private final static String QUEUE_DOC_AGREEMENT2 = "doc.agreement.extraWork";
    private final static String QUEUE_DOC_STATEMENT1 = "doc.statement.apply";
    private final static String QUEUE_DOC_STATEMENT2 = "doc.statement.advance";

    // роутинг по png
    private final static String APPLY_ROUTING_KEY = "apply";
    // роутинг по jpg
    private final static String ADVANCE_ROUTING_KEY = "advance";
    // есть новый экчендж
    private final static String STATEMENT_EXCHANGE = "statement_exchange";
    // новый
    private final static String EXCHANGE_TYPE = "direct";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            // создали Exchange
            channel.exchangeDeclare(STATEMENT_EXCHANGE, EXCHANGE_TYPE);
            // привязали очереди под определенным routingKey
            channel.queueBind(QUEUE_DOC_STATEMENT1, STATEMENT_EXCHANGE, APPLY_ROUTING_KEY);
            channel.queueBind(QUEUE_DOC_STATEMENT2, STATEMENT_EXCHANGE, ADVANCE_ROUTING_KEY);
            Person person = Person.getFalsePerson();
            String result = new ObjectMapper().writeValueAsString(person);
            System.out.println(result);
            channel.basicPublish(EXCHANGE, "", null, result.getBytes());
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            // создали Exchange
            channel.exchangeDeclare(STATEMENT_EXCHANGE, EXCHANGE_TYPE);
            // привязали очереди под определенным routingKey
            channel.queueBind(QUEUE_DOC_STATEMENT1, STATEMENT_EXCHANGE, APPLY_ROUTING_KEY);
            channel.queueBind(QUEUE_DOC_STATEMENT2, STATEMENT_EXCHANGE, ADVANCE_ROUTING_KEY);

            File file = new File("images.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String currentFile;
            while ((currentFile = reader.readLine()) != null) {
                // в зависимости от типа файла отправляем его в конкретную очередь
                String currentRouting = currentFile.substring(currentFile.lastIndexOf(".") + 1);

                if (currentRouting.equals("jpeg")) {
                    currentRouting = "jpg";
                }

                channel.basicPublish(IMAGES_EXCHANGE, currentRouting, null, currentFile.getBytes());
            }
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private boolean[] chooseDoc(){
        System.out.println("Напишите да, если вы желаете распечатать заявление о согласии на обработку личных данных:\n");
    }

    private boolean checkAnswer(String answer){
        final String YES = "да";
        return answer.equals(YES);
    }
}
