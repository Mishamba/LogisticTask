package com.logistic.project.model;

import com.logistic.project.util.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
public class Customer {
    @Id
    private String name;
    private Coordinate position;
}
