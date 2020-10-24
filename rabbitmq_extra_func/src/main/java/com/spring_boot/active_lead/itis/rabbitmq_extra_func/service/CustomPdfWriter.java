package com.spring_boot.active_lead.itis.rabbitmq_extra_func.service;

import com.spring_boot.active_lead.itis.rabbitmq_extra_func.model.Person;

public interface CustomPdfWriter {
    void createPdfs(Person person);
    void createDocumentOne(String fileName, Person person);

    void createDocumentTwo(String fileName, Person person);

    void createDocumentThree(String fileName, Person person);
}
