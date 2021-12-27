package com.logistic.project.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

@Data
public class Order {
    @Id
    private String id;
    @Field(name = "routes")
    private List<Route> routeList;
    @Field(name = "customer")
    private Customer customer;
    @Field(name = "orderPositions")
    private Map<String, Integer> resourceQuantity;
}
