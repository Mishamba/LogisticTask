package com.logistic.project.util;

import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.commons.lang.ObjectUtils;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateCalculatorTest {

    @Test
    void getCoordinateDistance() {
        Coordinate coordinate1 = new Coordinate(0, 0);
        Coordinate coordinate2 = new Coordinate(3, 4);
        Coordinate coordinate3 = new Coordinate(1, 1);
        Coordinate coordinate4 = new Coordinate(4, -3);
        Coordinate coordinate5 = new Coordinate(1, 0);
        Coordinate coordinate6 = new Coordinate(2, 0);
        Coordinate coordinate7 = new Coordinate(10, 10);
        Coordinate coordinate8 = new Coordinate(10, 15);

        double expected1_2_4 = 5.0;
        double actual1 = CoordinateCalculator.getCoordinateDistance(coordinate1, coordinate2);
        double actual2 = CoordinateCalculator.getCoordinateDistance(coordinate3, coordinate4);
        double expected3 = 1.0;
        double actual3 = CoordinateCalculator.getCoordinateDistance(coordinate5, coordinate6);
        double actual4 = CoordinateCalculator.getCoordinateDistance(coordinate7, coordinate8);

        assertEquals(expected1_2_4, actual1);
        assertEquals(expected1_2_4, actual2);
        assertEquals(expected3, actual3);
        assertEquals(expected1_2_4, actual4);
    }

    @Test
    void getCoordinateDistanceException() {
        assertThrows(NullPointerException.class, () -> {
            CoordinateCalculator.getCoordinateDistance(null, null);
        });
    }
}