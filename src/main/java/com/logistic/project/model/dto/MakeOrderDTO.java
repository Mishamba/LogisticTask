package com.logistic.project.model.dto;

import lombok.Data;

import java.util.Map;

@Data
public class MakeOrderDTO {
    private CustomerDTO customerDTO;
    private Map<String, Integer> merchandiseQuantity;
}
