package com.itis.spring_boot.active_lead.querydsl.repos;

import com.itis.spring_boot.active_lead.querydsl.model.Country;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends MongoRepository<Country, String> {
    Country findByName(String name);
}
