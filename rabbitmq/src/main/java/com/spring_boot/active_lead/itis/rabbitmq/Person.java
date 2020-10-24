package com.spring_boot.active_lead.itis.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person{
    private String firstName;
    private String secondName;
    private String passwordNumber;
    private String age;
    private String passwordIssue;
}