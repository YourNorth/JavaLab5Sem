package com.spring_boot.active_lead.itis.rabbitmq_extra_func;

import com.spring_boot.active_lead.itis.rabbitmq_extra_func.model.Person;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.service.CustomPdfWriter;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.service.CustomPdfWriterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqExtraFuncApplication {

    public static void main(String[] args) {

        SpringApplication.run(RabbitmqExtraFuncApplication.class, args);
        new CustomPdfWriterImpl().createPdfs(Person.getFalsePerson());
    }

}
