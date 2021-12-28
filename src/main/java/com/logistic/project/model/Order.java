package com.logistic.project.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
@Builder
public class Order {
    private Set<Warehouse> warehouses;
    private Customer customer;
    private Map<String, Integer> merchandiseQuantity;

    public void addWarehouse(Warehouse warehouseToAdd) {
        warehouses.add(warehouseToAdd);
    }
}
