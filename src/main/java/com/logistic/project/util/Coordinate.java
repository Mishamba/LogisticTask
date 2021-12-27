package com.logistic.project.util;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class Coordinate {
    @Min(0)
    private Integer x;
    @Min(0)
    private Integer y;
}
