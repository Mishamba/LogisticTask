package com.logistic.project.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Route {
    @Field(name = "recipient")
    private Customer to;
    @Field(name = "sender")
    private Warehouse from;
    @Field(name = "distance")
    private int distance;
}
