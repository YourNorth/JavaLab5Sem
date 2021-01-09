package com.itis.spring_boot.active_lead.querydsl.repos;

import com.itis.spring_boot.active_lead.querydsl.dto.CompanyDto;
import com.itis.spring_boot.active_lead.querydsl.dto.CompanyRequest;
import com.itis.spring_boot.active_lead.querydsl.model.Company;
import com.itis.spring_boot.active_lead.querydsl.model.QCompany;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyByRequestRepositoryImpl implements CompanyByRequestRepository{

    final CompanyRepository companyRepository;

    public CompanyByRequestRepositoryImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<CompanyDto> findByRequest(CompanyRequest companyRequest) {
        BooleanBuilder predicate = new BooleanBuilder();

        List<Company> companies;

        if (companyRequest.getName() != null) {
            predicate.or(QCompany.company.name.containsIgnoreCase(companyRequest.getName()));
        }
        if (companyRequest.getCountry() != null) {
            predicate.or(QCompany.company.country().name.containsIgnoreCase(companyRequest.getCountry()));
        }

        return ((List<Company>) companyRepository.findAll(predicate))
                .stream()
                .map(company ->
                        CompanyDto.builder()
                                .name(company.getName())
                                .country(company.getCountry().getName())
                                .build())
                .collect(Collectors.toList());
    }
}
