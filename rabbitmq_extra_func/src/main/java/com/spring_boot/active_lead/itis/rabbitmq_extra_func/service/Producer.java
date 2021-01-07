package com.spring_boot.active_lead.itis.rabbitmq_extra_func.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class Producer {
    private final InfoProducerTopic topicProducer;
    private final InfoProducerFanout fanoutProducer;

    private final static String APPLY = "doc.agreement.apply";
    private final static String PERSONAL_DATE = "doc.agreement.personalDate";
    private final static String EXTRA_WORK = "doc.agreement.extraWork";
    private final static String ADVANCE = "doc.statement.advance";

    // роутинг по соглашению на работу : APPLY
    private final static String APPLY_ROUTING_KEY = "apply";
    // роутинг по соглашению на работу с дополнительными рабочими часами: APPLY, EXTRA_WORK
    private final static String APPLY_PLUS_EXTRA_WORK_ROUTING_KEY = "applyWithExtraWork";
    // роутинг по заявлению на дополнительные часы за аванс: EXTRA_WORK, ADVANCE
    private final static String ADVANCE_PLUS_EXTRA_WORK_ROUTING_KEY = "extraWorkPlusAdvance";

    // создадим новый эксчейндж
    private final static String STATEMENT_EXCHANGE = "statement_exchange";
    // присвоим ему тип direct
    private final static String EXCHANGE_TYPE = "direct";

    private final Channel channel;

    public Producer(InfoProducerTopic topicProducer, InfoProducerFanout fanoutProducer) {
        this.topicProducer = topicProducer;
        this.fanoutProducer = fanoutProducer;
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try {
            //создадим подключение
            Connection connection = connectionFactory.newConnection();
            //создадим канал
            channel = connection.createChannel();
            // создали Exchange
            channel.exchangeDeclare(STATEMENT_EXCHANGE, EXCHANGE_TYPE);
            // привязали очереди под определенным routingKey
            channel.queueBind(APPLY, STATEMENT_EXCHANGE, APPLY_ROUTING_KEY);
            channel.queueBind(PERSONAL_DATE, STATEMENT_EXCHANGE, APPLY_ROUTING_KEY);
            channel.queueBind(APPLY, STATEMENT_EXCHANGE, APPLY_PLUS_EXTRA_WORK_ROUTING_KEY);
            channel.queueBind(PERSONAL_DATE, STATEMENT_EXCHANGE, APPLY_PLUS_EXTRA_WORK_ROUTING_KEY);
            channel.queueBind(EXTRA_WORK, STATEMENT_EXCHANGE, APPLY_PLUS_EXTRA_WORK_ROUTING_KEY);
            channel.queueBind(EXTRA_WORK, STATEMENT_EXCHANGE, ADVANCE_PLUS_EXTRA_WORK_ROUTING_KEY);
            channel.queueBind(ADVANCE, STATEMENT_EXCHANGE, ADVANCE_PLUS_EXTRA_WORK_ROUTING_KEY);
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void pushRoutingApply(Person person){
        try {
            channel.basicPublish(STATEMENT_EXCHANGE, APPLY_ROUTING_KEY, null, person.toJson().getBytes());
        } catch (IOException e) {
            log.warn(e.getLocalizedMessage());
        }
    }

    public void pushRoutingApplyPlusExtraWork(Person person){
        try {
            channel.basicPublish(STATEMENT_EXCHANGE, APPLY_PLUS_EXTRA_WORK_ROUTING_KEY, null, person.toJson().getBytes());
        } catch (IOException e) {
            log.warn(e.getLocalizedMessage());
        }
    }

    public void pushRoutingAdvancePlusExtraWork(Person person){
        try {
            channel.basicPublish(STATEMENT_EXCHANGE, ADVANCE_PLUS_EXTRA_WORK_ROUTING_KEY, null, person.toJson().getBytes());
        } catch (IOException e) {
            log.warn(e.getLocalizedMessage());
        }

    }

    public void pushAgreement(Person person){
        topicProducer.createAgreements(person);
    }

    public void pushStatement(Person person){
        topicProducer.createStatements(person);
    }

    public void pushAll(Person person){
        fanoutProducer.createDocs(person);
    }
}
