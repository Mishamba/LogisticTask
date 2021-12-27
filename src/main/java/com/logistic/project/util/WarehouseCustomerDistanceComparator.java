package com.logistic.project.util;

import com.logistic.project.model.Customer;
import com.logistic.project.model.Warehouse;
import lombok.AllArgsConstructor;

import java.util.Comparator;

@AllArgsConstructor
public class WarehouseCustomerDistanceComparator implements Comparator<Warehouse> {
    private Customer customer;

    @Override
    public int compare(Warehouse o1, Warehouse o2) {
        int result = 0;

        if (CoordinateCalculator.getCoordinateDistance(o1.getPosition(), customer.getPosition()) >
                CoordinateCalculator.getCoordinateDistance(o2.getPosition(), customer.getPosition())) {
            result = 1;
        } else
        if (CoordinateCalculator.getCoordinateDistance(o1.getPosition(), customer.getPosition()) <
                CoordinateCalculator.getCoordinateDistance(o2.getPosition(), customer.getPosition())){
            result = -1;
        }

        return result;
    }
}
