package com.spring_boot.active_lead.itis.rabbitmq_extra_func.service;

import com.spring_boot.active_lead.itis.rabbitmq_extra_func.model.Person;

public interface CustomPdfWriter {
    void createPdfs(Person person);
    void createApplyAgreement(String fileName, Person person);

    void createPersonalDateAgreement(String fileName, Person person);

    void createAdvanceStatement(String fileName, Person person);
}
