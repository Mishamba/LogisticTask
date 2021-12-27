package com.logistic.project.model;

import lombok.Data;

import java.util.Map;

@Data
public class Warehouse {
    private String name;
    private Map<Resource, Integer> resourceQuantity;

    public Warehouse(String name, Map<Resource, Integer> resourceQuantity) {
        this.name = name;
        this.resourceQuantity = resourceQuantity;
    }
}
