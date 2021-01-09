package com.itis.spring_boot.active_lead.querydsl.repos;

import com.itis.spring_boot.active_lead.querydsl.model.Company;
import com.itis.spring_boot.active_lead.querydsl.model.Country;
import com.itis.spring_boot.active_lead.querydsl.model.QCompany;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String>, QuerydslPredicateExecutor<Company>, QuerydslBinderCustomizer<QCompany> {
    Company findByName(String name);
    @Override
    default void customize(QuerydslBindings bindings, QCompany qCompany) {
        bindings.bind(qCompany.country().name).as("country.name").first(
                StringExpression::containsIgnoreCase
        );
    }
}
