package com.itis.spring_boot.active_lead.querydsl.controller;

import com.itis.spring_boot.active_lead.querydsl.dto.CompanyDto;
import com.itis.spring_boot.active_lead.querydsl.dto.CompanyRequest;
import com.itis.spring_boot.active_lead.querydsl.repos.CompanyByRequestRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class SearchController {

    private final CompanyByRequestRepository companyByRequestRepository;

    public SearchController(CompanyByRequestRepository companyByRequestRepository) {
        this.companyByRequestRepository = companyByRequestRepository;
    }


    @GetMapping("/company/search")
    public ResponseEntity<List<CompanyDto>> searchByRequest(CompanyRequest companyRequest) {
        return ResponseEntity.ok(companyByRequestRepository.findByRequest(companyRequest));
    }
/*
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/accounts/search")
    public ResponseEntity<List<CompanyDto>> searchByPredicate(@QuerydslPredicate(root = Company.class, bindings = CompanyRepository.class) Predicate predicate) {
        return ResponseEntity.ok(
                StreamSupport.stream(companyRepository.findAll(predicate).spliterator(), true)
                        .map(company ->
                                CompanyDto.builder()
                                        .name(company.getName())
                                        .country(company.getCountry())
                                        .build()).collect(Collectors.toList()));
    }*/
}

