package com.logistic.project.repository;

import com.logistic.project.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    @Query("{'customer._id' : ?0 }")
    List<Order> findOrderByCustomerName(String customerName);
}
