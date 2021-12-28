package com.logistic.project.model;

import com.logistic.project.util.Coordinate;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Customer {
    @Id
    private String name;
    private Coordinate position;
}
