package com.itis.spring_boot.active_lead.mongodb.jpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "country")
@Builder
public class Country {

    @Id
    private String _id;
    private String name;
    private String status;
}
