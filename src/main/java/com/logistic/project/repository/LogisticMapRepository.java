package com.logistic.project.repository;

import com.logistic.project.model.LogisticMap;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogisticMapRepository extends MongoRepository<LogisticMap, String> {
}
