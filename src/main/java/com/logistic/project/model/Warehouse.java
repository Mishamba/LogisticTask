package com.logistic.project.model;

import com.logistic.project.util.Coordinate;
import lombok.Data;

import java.util.Map;

@Data
public class Warehouse {
    private String name;
    private Map<String, Integer> merchandiseQuantity;
    private Coordinate position;
}
