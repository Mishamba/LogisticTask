package com.logistic.project.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoordinateCalculator {
    public static Double getCoordinateDistance(Coordinate coordinate1, Coordinate coordinate2) {
        return Math.sqrt(Math.pow((coordinate1.getX() - coordinate2.getX()), 2.0) +
                Math.pow(coordinate1.getY() - coordinate2.getY(), 2.0));
    }
}
