package com.logistic.project.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class Coordinate implements Serializable {
    @Min(0)
    private Integer x;
    @Min(0)
    private Integer y;
}
