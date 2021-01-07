package com.spring_boot.active_lead.itis.rabbitmq_extra_func.controller;

import com.spring_boot.active_lead.itis.rabbitmq_extra_func.model.Person;
import com.spring_boot.active_lead.itis.rabbitmq_extra_func.service.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class InputController {
    @Autowired
    Producer producer;

    @PostMapping({"/create_with_routing_apply", "/create1"})
    void createAgreements(@RequestBody Person person){
        producer.pushRoutingApply(person);
        log.info("{} : person : {} ", "createAgreements()", person);
    }

    @PostMapping({"/create_with_routing_apply_plus_extra_work", "/create2"})
    void createRoutingApplyPlusExtraWork(@RequestBody Person person){
        producer.pushRoutingApplyPlusExtraWork(person);
        log.info("{} : person : {} ", "createRoutingApplyPlusExtraWork()", person);
    }

    @PostMapping({"/create_with_routing_advance_plus_extra_work", "/create3"})
    void createRoutingAdvancePlusExtraWork(@RequestBody Person person){
        producer.pushRoutingAdvancePlusExtraWork(person);
        log.info("{} : person : {} ", " createRoutingAdvancePlusExtraWork()", person);

    }

    @PostMapping({"/createAgreement", "/create4"})
    void createAgreement(@RequestBody Person person){
        producer.pushAgreement(person);
        log.info("{} : person : {} ", "createAgreement()", person);

    }

    @PostMapping({"/createStatement", "/create5"})
    void createStatement(@RequestBody Person person){
        producer.pushStatement(person);
        log.info("{} : person : {} ", "createStatement()", person);

    }

    @PostMapping({"/createAll", "/create6"})
    void createAll(@RequestBody Person person){
        producer.pushAll(person);
        log.info("{} : person : {} ", "createAll()", person);

    }


}
