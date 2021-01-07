package com.spring_boot.active_lead.itis.rabbitmq_extra_func.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.model.Person;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class InfoProducerFanout {

    private static final String EXCHANGE_TYPE = "fanout";
    public static final String FANOUT_EXCHANGE = "exchange_fanout";
    Channel channel;

    public InfoProducerFanout(){
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(FANOUT_EXCHANGE, EXCHANGE_TYPE);
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void createDocs(Person person){
        try {
            channel.basicPublish(FANOUT_EXCHANGE, "", null, person.toJson().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
