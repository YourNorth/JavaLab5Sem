package com.itis.spring_boot.active_lead.querydsl.dto;


import com.itis.spring_boot.active_lead.querydsl.model.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyRequest {

    private String name;
    private String country;
}
