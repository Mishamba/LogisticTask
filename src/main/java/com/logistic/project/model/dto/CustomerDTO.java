package com.logistic.project.model.dto;

import com.logistic.project.util.Coordinate;
import lombok.Data;

@Data
public class CustomerDTO {
    private String name;
    private Coordinate position;
}
