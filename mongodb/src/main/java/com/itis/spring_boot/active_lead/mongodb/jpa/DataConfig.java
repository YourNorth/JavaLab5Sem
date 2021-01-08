package com.itis.spring_boot.active_lead.mongodb.jpa;

import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.itis.spring_boot.active_lead.mongodb.jpa")
public class DataConfig {

    @Bean
    public MongoTemplate mongoTemplate(){
        return new MongoTemplate(MongoClients.create(),"jobboard");
    }
}
