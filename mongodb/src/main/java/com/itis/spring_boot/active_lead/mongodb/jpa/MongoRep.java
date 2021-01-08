package com.itis.spring_boot.active_lead.mongodb.jpa;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Example;

public class MongoRep {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(DataConfig.class);
        CountryRepository countryRepository = context.getBean(CountryRepository.class);
        countryRepository.insert(Country.builder().name("Chinese").status("cool").build());
        countryRepository.findAll().forEach(System.out::println);
        System.out.println("==================");

        Country country = countryRepository.findByName("Chinese");
        country.setStatus("good");
        countryRepository.save(country);
        countryRepository.findAll().forEach(System.out::println);
        System.out.println("==================");

        countryRepository.deleteByName("Chinese");
        countryRepository.findAll().forEach(System.out::println);
        System.out.println("==================");
    }
}
