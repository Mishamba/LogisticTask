package com.logistic.project.model;

import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class Order {
    private Set<Warehouse> warehouses;
    private Customer customer;
    private Map<String, Integer> merchandiseQuantity;
    @Min(0)
    private Double cost;

    public Order(Set<Warehouse> warehouses, Customer customer, Map<String, Integer> merchandiseQuantity, Double cost) {
        if (warehouses == null) {
            this.warehouses = new HashSet<>();
        }

        this.customer = customer;
        this.merchandiseQuantity = merchandiseQuantity;
        this.cost = cost;
    }

    public void addWarehouse(Warehouse warehouseToAdd) {
        warehouses.add(warehouseToAdd);
    }
}
