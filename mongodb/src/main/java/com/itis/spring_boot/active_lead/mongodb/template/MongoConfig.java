package com.itis.spring_boot.active_lead.mongodb.template;

import com.itis.spring_boot.active_lead.mongodb.driver.MongoDriver;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Bean
    public MongoTemplate mongoTemplate(){
        return new MongoTemplate(MongoClients.create(), "jobboard");
    }

}