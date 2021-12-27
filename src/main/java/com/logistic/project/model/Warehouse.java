package com.logistic.project.model;

import lombok.Data;

import java.util.Map;

@Data
public class Warehouse {
    private String name;
    private Map<String, Integer> resourceQuantity;

    public Warehouse(String name, Map<String, Integer> resourceQuantity) {
        this.name = name;
        this.resourceQuantity = resourceQuantity;
    }
}
