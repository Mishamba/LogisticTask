package com.logistic.project.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Order {
    private Warehouse warehouse;
    private Customer customer;
    private Double distance;
    private Map<String, Integer> merchandiseQuantity;
}
