package com.itis.spring_boot.active_lead.annotation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Form {
    String action;
    String method;
    List<Input> inputs;
}
