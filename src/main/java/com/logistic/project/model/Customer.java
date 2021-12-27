package com.logistic.project.model;

import com.logistic.project.util.Coordinate;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Customer {
    @Id
    private String name;
    @Field(name = "position")
    private Coordinate position;
}
