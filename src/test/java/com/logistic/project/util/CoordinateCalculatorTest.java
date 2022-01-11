package com.logistic.project.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testcontainers.shaded.org.apache.commons.lang.ObjectUtils;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateCalculatorTest {

    @ParameterizedTest
    @MethodSource("provideCoordinatesAndDistances")
    void getCoordinateDistance(Coordinate firstCoordinate, Coordinate secondCoordinate, double expectedDistance) {
        double actualDistance = CoordinateCalculator.getCoordinateDistance(firstCoordinate, secondCoordinate);
        assertEquals(expectedDistance, actualDistance);
    }

    private static Stream<Arguments> provideCoordinatesAndDistances() {
        return Stream.of(
                Arguments.of(new Coordinate(0, 0), new Coordinate(3, 4), 5.0),
                Arguments.of(new Coordinate(1, 1), new Coordinate(4, -3), 5.0),
                Arguments.of(new Coordinate(1, 0), new Coordinate(2, 0), 1.0),
                Arguments.of(new Coordinate(10, 10), new Coordinate(10, 15), 5.0)
        );
    }

    @Test
    void getCoordinateDistanceException() {
        assertThrows(NullPointerException.class, () -> {
            CoordinateCalculator.getCoordinateDistance(null, null);
        });
    }
}