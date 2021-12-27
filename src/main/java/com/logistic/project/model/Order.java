package com.logistic.project.model;

import lombok.Data;

@Data
public class Order {
    private Customer to;
    private Warehouse from;
    private int distance;
}
