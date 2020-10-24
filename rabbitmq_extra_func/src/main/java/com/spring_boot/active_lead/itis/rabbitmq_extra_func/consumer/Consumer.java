package com.spring_boot.active_lead.itis.rabbitmq_extra_func.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.model.Person;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.service.CustomPdfWriterImpl;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class Consumer {
    private final static String EXCHANGE_NAME = "person_info";
    private final static String EXCHANGE_TYPE = "fanout";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicQos(2);

            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            // создаем временную очередь со случайным названием
            String queue = channel.queueDeclare().getQueue();

            // привязали очередь к EXCHANGE_NAME
            channel.queueBind(queue, EXCHANGE_NAME, "");

            DeliverCallback deliverCallback = (consumerTag, message) -> {
                Person person = new ObjectMapper().readValue(message.getBody(), Person.class);
                System.out.println("Person: " + person);
                new CustomPdfWriterImpl().createPdfs(person);
                System.out.println("that`s all");
            };

            channel.basicConsume(queue, false, deliverCallback, consumerTag -> {});
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
