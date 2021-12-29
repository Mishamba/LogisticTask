package com.logistic.project.repository.impl;

import com.logistic.project.model.Warehouse;
import com.logistic.project.repository.CustomWarehouseRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class CustomWarehouseRepositoryImpl implements CustomWarehouseRepository {
    private MongoTemplate mongoTemplate;

    @Override
    public List<Warehouse> findWarehousesByMerchandiseQuantityContaining(Map<String, Integer> merchandiseQuantity) {
        Query query = new Query();
        merchandiseQuantity.forEach((key, value) -> query.addCriteria(Criteria.where(key).gte(value)));
        return mongoTemplate.find(query, Warehouse.class);
    }
}
