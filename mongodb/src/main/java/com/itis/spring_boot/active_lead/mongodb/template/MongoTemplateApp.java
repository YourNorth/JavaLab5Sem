package com.itis.spring_boot.active_lead.mongodb.template;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class MongoTemplateApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MongoConfig.class);
        MongoTemplate template = context.getBean(MongoTemplate.class);
        List<Country> countries;
//        Teacher teacher = new Teacher("Преподаватель", "Преподавателев");
//        template.save(teacher, "new_teacher");

        // db.courses.find({active: true, $or: [{keywords: 'java core'}, {studentsCount: {$lt: 35}}]})
        countries = template.findAll(Country.class, "country");
        System.out.println(countries);
        System.out.println("====================");

        countries = template.find(new Query(
                where("name").is("USA")) , Country.class, "country");
        System.out.println(countries);
        System.out.println("====================");

        template.insert(Country.builder().name("Japan").build(),"country");
        countries = template.findAll(Country.class, "country");
        System.out.println(countries);
        System.out.println("====================");

        template.findAndModify(new Query(where("name").is("Japan")), new Update().set("status", "good"), Country.class, "country");
        countries = template.findAll(Country.class, "country");
        System.out.println(countries);
        System.out.println("====================");

        template.findAndRemove(new Query(where("name").is("Japan")),Country.class, "country");
        countries = template.findAll(Country.class, "country");
        System.out.println(countries);
        System.out.println("====================");
    }
}
