package com.spring_boot.active_lead.itis.rabbitmq_extra_func.model;


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

    public static Person getFalsePerson() {
        return Person.builder()
                .age("wfgwe")
                .firstName("fwef")
                .passwordIssue("1wef")
                .passwordNumber("wef")
                .secondName("afwef")
                .build();
    }
}
