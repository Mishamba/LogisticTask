package com.logistic.project.model;

import lombok.Data;

import java.util.Map;

@Data
public class Warehouse {
    private String name;
    private Map<String, Integer> merchandiseQuantity;

    public Warehouse(String name, Map<String, Integer> merchandiseQuantity) {
        this.name = name;
        this.merchandiseQuantity = merchandiseQuantity;
    }
}
