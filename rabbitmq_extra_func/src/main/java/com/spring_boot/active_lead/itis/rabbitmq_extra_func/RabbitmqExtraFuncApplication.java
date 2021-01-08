package com.spring_boot.active_lead.itis.rabbitmq_extra_func;

import com.rabbitmq.client.ConnectionFactory;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.consumer.AdvancePrinter;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.consumer.ApplyPrinter;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.consumer.ExtraWorkPrinter;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.consumer.PersonalDatePrinter;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.model.Person;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.service.InfoProducerFanout;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.service.InfoProducerTopic;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.service.CustomPdfWriterImpl;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.service.Producer;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Data
@SpringBootApplication
public class RabbitmqExtraFuncApplication {

    @Autowired
    Producer producer;

    @Autowired
    AdvancePrinter advancePrinter;

    @Autowired
    ApplyPrinter applyPrinter;

    @Autowired
    ExtraWorkPrinter extraWorkPrinter;

    @Autowired
    PersonalDatePrinter personalDatePrinter;
    public static void main(String[] args) {
        SpringApplication.run(RabbitmqExtraFuncApplication.class, args);
        /*System.out.println(Person.builder()
                .firstName("wefwe")
                .secondName("wefwefwef")
                .passwordIssue("wefwefwef")
                .age("wefw")
                .passwordNumber("wef")
                .build()
                .toJson());*/
    }

}
