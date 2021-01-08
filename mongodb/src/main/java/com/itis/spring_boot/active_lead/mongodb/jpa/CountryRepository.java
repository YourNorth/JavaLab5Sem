package com.itis.spring_boot.active_lead.mongodb.jpa;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CountryRepository extends MongoRepository<Country, String> {
    Country findByName(String name);

    void deleteByName(String name);
}
