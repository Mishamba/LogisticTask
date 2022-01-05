package com.logistic.project.repository;

import com.logistic.project.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    @Query("{'customer._id' : ?0 }")
    Page<Order> findOrderByCustomerName(String customerName, Pageable pageable);
}
