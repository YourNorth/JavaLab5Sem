package com.itis.spring_boot.active_lead.querydsl.repos;

import com.itis.spring_boot.active_lead.querydsl.dto.CompanyDto;
import com.itis.spring_boot.active_lead.querydsl.dto.CompanyRequest;

import java.util.List;

public interface CompanyByRequestRepository {
    List<CompanyDto> findByRequest(CompanyRequest requestCompany);
}
