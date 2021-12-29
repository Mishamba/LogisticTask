package com.logistic.project.controller;

import com.logistic.project.model.Customer;
import com.logistic.project.model.Order;
import com.logistic.project.model.Warehouse;
import com.logistic.project.model.dto.MakeOrderDTO;
import com.logistic.project.repository.impl.CustomWarehouseRepositoryImpl;
import com.logistic.project.repository.CustomerRepository;
import com.logistic.project.util.CoordinateCalculator;
import com.logistic.project.util.WarehouseCustomerDistanceComparator;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class LogisticController {
    private final CustomerRepository customerRepository;
    private final CustomWarehouseRepositoryImpl customWarehouseRepositoryImpl;

    @PostMapping
    public Customer createNewCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @GetMapping
    public Order getOrderRouteAndCost(@RequestBody MakeOrderDTO makeOrderDTO) {
        Customer customer = customerRepository.findCustomerByName(makeOrderDTO.getCustomerDTO().getName());

        Order finalOrder = Order.builder().
                warehouse(null).
                customer(customer).
                merchandiseQuantity(makeOrderDTO.getMerchandiseQuantity()).
                build();

        List<Warehouse> warehouses = customWarehouseRepositoryImpl.
                findWarehousesByMerchandiseQuantityContaining(makeOrderDTO.getMerchandiseQuantity());
        warehouses.sort(new WarehouseCustomerDistanceComparator(customer));

        Warehouse finalWarehouse = warehouses.get(0);

        finalOrder.setWarehouse(finalWarehouse);
        finalOrder.setDistance(CoordinateCalculator.getCoordinateDistance(customer.getPosition(), finalWarehouse.getPosition()));

        return finalOrder;
    }
}
