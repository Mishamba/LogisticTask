package com.logistic.project.model;

import com.logistic.project.util.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Map;

@Data
@Document
@AllArgsConstructor
@Builder
public class Warehouse implements Serializable {
    @Id
    private String name;
    private Map<String, Integer> merchandiseQuantity;
    private Coordinate position;
}
