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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/logistic")
@AllArgsConstructor
public class LogisticController {
    private final CustomerRepository customerRepository;
    private final CustomWarehouseRepositoryImpl customWarehouseRepositoryImpl;

    @PostMapping(path = "/customer/create")
    public Customer createNewCustomer(@RequestBody Customer customer) {
        System.out.println(customer.toString());
        return customerRepository.save(customer);
    }

    @GetMapping("/customer/get")
    public Customer getCustomerByName(@RequestParam(name = "name") String name) {
        return customerRepository.findCustomerByName(name);
    }

    @GetMapping("/order/get")
    public Order getOrderRouteAndCost(@RequestBody MakeOrderDTO makeOrderDTO) {
        Customer customer = customerRepository.findCustomerByName(makeOrderDTO.getCustomerDTO().getName());
        List<Warehouse> warehouses = customWarehouseRepositoryImpl.
                findWarehousesByMerchandiseQuantityContaining(makeOrderDTO.getMerchandiseQuantity());

        Order finalOrder = Order.builder().
                customer(customer).
                merchandiseQuantity(makeOrderDTO.getMerchandiseQuantity()).
                build();

        warehouses.sort(new WarehouseCustomerDistanceComparator(customer));

        Warehouse finalWarehouse = warehouses.get(0);

        finalOrder.setWarehouse(finalWarehouse);
        finalOrder.setDistance(CoordinateCalculator.getCoordinateDistance(customer.getPosition(), finalWarehouse.getPosition()));

        return finalOrder;
    }
}
