package com.spring_boot.active_lead.itis.rabbitmq_extra_func.model;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public String toJson(){
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}

