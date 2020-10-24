package com.spring_boot.active_lead.itis.rabbitmq_extra_func.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeoutException;

public class PdfGroupProducer {
    private final static String DOC_ROUTING_KEY = "doc.#";
    private final static String DOC_EXCHANGE = "doc_topic_exchange";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicQos(3);

            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, DOC_EXCHANGE,DOC_ROUTING_KEY);
            channel.basicConsume(queueName, false, (consumerTag, message) -> {

                String fileUrl = new String(message.getBody());

            }, consumerTag -> {});
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
