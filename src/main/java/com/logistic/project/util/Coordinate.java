package com.logistic.project.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
public class Coordinate {
    @Min(0)
    private Integer x;
    @Min(0)
    private Integer y;
}
