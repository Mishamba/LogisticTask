package com.logistic.project.model;

import com.logistic.project.util.Coordinate;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document
public class Warehouse {
    @Id
    private String name;
    private Map<String, Integer> merchandiseQuantity;
    private Coordinate position;
}
