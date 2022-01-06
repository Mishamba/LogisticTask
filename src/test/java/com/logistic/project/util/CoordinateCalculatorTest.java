package com.logistic.project.util;

import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.commons.lang.ObjectUtils;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateCalculatorTest {

    @Test
    void getCoordinateDistance() {
        Coordinate coordinate1 = new Coordinate(0, 0);
        Coordinate coordinate2 = new Coordinate(3, 4);

        double expected = 5.0;
        double actual = CoordinateCalculator.getCoordinateDistance(coordinate1, coordinate2);
        assertEquals(expected, actual);
    }

    @Test
    void getCoordinateDistanceException() {
        assertThrows(NullPointerException.class, () -> {
            CoordinateCalculator.getCoordinateDistance(null, null);
        });
    }
}