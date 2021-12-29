package com.logistic.project.repository;

import com.logistic.project.model.Warehouse;

import java.util.List;
import java.util.Map;

public interface CustomWarehouseRepository {
    List<Warehouse> findWarehousesByMerchandiseQuantityContaining(Map<String, Integer> merchandiseQuantity);
}
