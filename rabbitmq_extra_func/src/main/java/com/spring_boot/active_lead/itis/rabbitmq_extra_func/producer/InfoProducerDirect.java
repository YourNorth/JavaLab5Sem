package com.spring_boot.active_lead.itis.rabbitmq_extra_func.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.model.Person;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.service.InfoProducerFanout;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.service.InfoProducerTopic;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

@Slf4j
public class InfoProducerDirect {

    private final static Scanner in = new Scanner(System.in);
    private final static String OUTPUT_FOR_NAME = "Введите имя: ";
    private final static String OUTPUT_FOR_SECOND_NAME = "Введите фамилию";
    private final static String OUTPUT_FOR_PASSPORT_NUMBER = "Введите номер паспорта: ";
    private final static String OUTPUT_FOR_AGE = "Введите ваш возраст: ";
    private final static String OUTPUT_FOR_PASSPORT_ISSUE = "Введите дату выдачи паспорта: ";


    private final static String APPLY = "doc.agreement.apply";
    private final static String PERSONAL_DATE = "doc.agreement.personalDate";
    private final static String EXTRA_WORK = "doc.agreement.extraWork";
    private final static String ADVANCE = "doc.statement.advance";

    private static String[] ROUTING_KEYS = new String[3];
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

    public static void main(String[] args) {
        //Наполним массив ключами
        ROUTING_KEYS[0] = APPLY_ROUTING_KEY;
        ROUTING_KEYS[1] = APPLY_PLUS_EXTRA_WORK_ROUTING_KEY;
        ROUTING_KEYS[2] = ADVANCE_PLUS_EXTRA_WORK_ROUTING_KEY;

        InfoProducerTopic topicProducer= new InfoProducerTopic();
        InfoProducerFanout fanoutProducer = new InfoProducerFanout();
        //создадим фабрику подключений к RMQ по локальной сети
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try {
            //создадим подключение
            Connection connection = connectionFactory.newConnection();
            //создадим канал
            Channel channel = connection.createChannel();
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
            //создаем персону для тестирования
            Person person = Person.getFalsePerson();
            //Person person = getPerson();
            //переводим ее в JSON
            String result = person.toJson();
            //Дебаги=)
            System.out.println(result);

            //Пользователем выбираются документы, которые ему необходимо распечатать
            //boolean[] answers = chooseDoc();
            //тестовый массив:
            boolean[] answers = new boolean[]{false,false,false,false,false, true};
            log.info("InfoProducerDirect: {}", Arrays.toString(answers));
            //в зависимости от ввода пользователя создаем те или иные документы
            for (int i = 0; i < ROUTING_KEYS.length; i++) {
                if(answers[i]){
                    channel.basicPublish(STATEMENT_EXCHANGE, ROUTING_KEYS[i], null, result.getBytes());
                }
            }
            if(answers[3]){
                topicProducer.createAgreements(person);
            }
            if(answers[4]){
                topicProducer.createStatements(person);
            }
            if(answers[5]){
                fanoutProducer.createDocs(person);
            }
            in.close();
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static boolean[] chooseDoc(){
        boolean[] answers = new boolean[6];

        System.out.println("Напишите да, если вы хотите распечатать заявления для принятия в штат:");
        answers[0]=checkAnswer(in.nextLine());

        System.out.println("Напишите да, если вы хотите распечатать заявления для принятия в штат с дополнительными рабочими часами:");
        answers[1]=checkAnswer(in.nextLine());

        System.out.println("Напишите да, если вы хотите распечатать заявление о получении аванса при дополнительных рабочих часах:");
        answers[2]=checkAnswer(in.nextLine());

        System.out.println("Напишите да, если вы хотите распечатать все соглашения:");
        answers[3]=checkAnswer(in.nextLine());

        System.out.println("Напишите да, если вы хотите распечатать все заявления:");
        answers[4]=checkAnswer(in.nextLine());

        System.out.println("Напишите да, если вы хотите распечатать рандомный документ:");
        answers[5]=checkAnswer(in.nextLine());

        return answers;
    }

    private static boolean checkAnswer(String answer){
        final String YES = "да";
        return answer.equals(YES);
    }

    private static String getValueFromConsole(Scanner in, String output) {
        System.out.println(output);
        return in.nextLine();
    }

    private static Person getPerson() {
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
