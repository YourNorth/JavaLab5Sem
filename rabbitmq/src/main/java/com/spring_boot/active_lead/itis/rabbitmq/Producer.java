package com.spring_boot.active_lead.itis.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.Builder;
import lombok.Data;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Producer {
    // есть EXCHANGE - images НЕ ОЧЕРЕДЬ
    private final static String EXCHANGE_NAME = "person_info";
    // тип FANOUT
    private final static String EXCHANGE_TYPE = "fanout";

    private final static String OUTPUT_FOR_NAME = "Введите имя: ";

    private final static String OUTPUT_FOR_SECOND_NAME = "Введите фамилию";

    private final static String OUTPUT_FOR_PASSPORT_NUMBER = "Введите номер паспорта: ";

    private final static String OUTPUT_FOR_AGE = "Введите ваш возраст: ";

    private final static String OUTPUT_FOR_PASSPORT_ISSUE = "Введите дату выдачи паспорта: ";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            // создаем exchange
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
           /* Person person = Person.builder()
                    .firstName()
                    .secondName()
                    .passwordNumber()
                    .age()
                    .passwordIssue()
                    .build();
*/
            Person person = getPerson();

            String result = new ObjectMapper().writeValueAsString(person);
            System.out.println(result);
            channel.basicPublish(EXCHANGE_NAME, "", null, result.getBytes());
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }

    }

    private static Person getFalsePerson() {
        return Person.builder()
                .age("wfgwe")
                .firstName("fwef")
                .passwordIssue("1wef")
                .passwordNumber("wef")
                .secondName("afwef")
                .build();
    }

    private static String getValueFromConsole(Scanner in, String output) {
        System.out.println(output);
        return in.nextLine();
    }

    private static Person getPerson() {
        Scanner in = new Scanner(System.in);
        String name = getValueFromConsole(in, OUTPUT_FOR_NAME);
        String secondName = getValueFromConsole(in, OUTPUT_FOR_SECOND_NAME);
        String passwordNumber = getValueFromConsole(in, OUTPUT_FOR_PASSPORT_NUMBER);
        String age = getValueFromConsole(in, OUTPUT_FOR_AGE);
        String passwordIssue = getValueFromConsole(in, OUTPUT_FOR_PASSPORT_ISSUE);
        return Person.builder()
                .firstName(name)
                .secondName(secondName)
                .age(age)
                .passwordIssue(passwordIssue)
                .passwordNumber(passwordNumber)
                .build();
    }
}