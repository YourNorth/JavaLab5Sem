package com.itis.spring_boot.active_lead.querydsl;

import com.itis.spring_boot.active_lead.querydsl.model.Company;
import com.itis.spring_boot.active_lead.querydsl.model.Country;
import com.itis.spring_boot.active_lead.querydsl.repos.CompanyRepository;
import com.itis.spring_boot.active_lead.querydsl.repos.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class QuerydslApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(QuerydslApplication.class, args);

       /* CountryRepository countryRepository = context.getBean(CountryRepository.class);
        CompanyRepository companyRepository = context.getBean(CompanyRepository.class);
        companyRepository.save(Company.builder().country(countryRepository.findByName("Russia")).name("MachineFactory").build());
        companyRepository.delete(Company.builder().name("Twitterworks").build());*/
    }


}
