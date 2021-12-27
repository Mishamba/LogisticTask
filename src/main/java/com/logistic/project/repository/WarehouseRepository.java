package com.logistic.project.repository;

import com.logistic.project.model.Warehouse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends MongoRepository<Warehouse, String> {
    public Warehouse findWarehouseByMerchandiseQuantityContaining(String merchandiseName);
}
