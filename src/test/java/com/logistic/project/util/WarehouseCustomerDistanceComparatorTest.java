package com.logistic.project.util;

import com.logistic.project.model.Customer;
import com.logistic.project.model.Warehouse;
import com.logistic.project.repository.WarehouseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseCustomerDistanceComparatorTest {

    @ParameterizedTest
    @MethodSource("provideCustomerAndWarehouses")
    void compare(Customer customer, Warehouse warehouse1, Warehouse warehouse2, int expected) {
        WarehouseCustomerDistanceComparator comparator = new WarehouseCustomerDistanceComparator(customer);

        int actual = comparator.compare(warehouse1, warehouse2);

        assertEquals(expected, actual);
    }

    private static Stream<Arguments> provideCustomerAndWarehouses() {
        return Stream.of(
                Arguments.of(
                        new Customer("smth", new Coordinate(0, 0)),
                        new Warehouse("first", new HashMap<>(), new Coordinate(3, 4)),
                        new Warehouse("second", new HashMap<>(), new Coordinate(4, 5)),
                        -1),
                Arguments.of(
                        new Customer("smth", new Coordinate(0, 0)),
                        new Warehouse("first", new HashMap<>(), new Coordinate(1, 1)),
                        new Warehouse("second", new HashMap<>(), new Coordinate(0, 0)),
                        1)
        );
    }
}