package com.itis.spring_boot.active_lead.querydsl.model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@QueryEntity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "country")
@Builder
public class Country {

    @Id
    private String _id;
    private String name;
}
