package com.logistic.project.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Order {
    private List<Warehouse> warehouses;
    private Customer customer;
    private Map<String, Integer> merchandiseQuantity;
    private Integer cost;
}
