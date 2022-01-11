package com.logistic.project.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Map;

@Document
@Data
@Builder
public class Order implements Serializable {
    @Id
    private String id;
    private Warehouse warehouse;
    private Customer customer;
    private Double distance;
    private Map<String, Integer> merchandiseQuantity;
}
