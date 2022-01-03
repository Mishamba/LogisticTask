package com.logistic.project.controller;

import com.logistic.project.model.Customer;
import com.logistic.project.model.Order;
import com.logistic.project.model.Warehouse;
import com.logistic.project.model.dto.MakeOrderDTO;
import com.logistic.project.repository.OrderRepository;
import com.logistic.project.repository.WarehouseRepository;
import com.logistic.project.repository.impl.CustomWarehouseRepositoryImpl;
import com.logistic.project.repository.CustomerRepository;
import com.logistic.project.util.CoordinateCalculator;
import com.logistic.project.util.WarehouseCustomerDistanceComparator;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class LogisticController {
    private final CustomerRepository customerRepository;
    private final CustomWarehouseRepositoryImpl customWarehouseRepositoryImpl;
    private final WarehouseRepository warehouseRepository;
    private final OrderRepository orderRepository;

    @GetMapping("/orders")
    public List<Order> findOrders(@RequestParam("customerName") String customerName) {
        return orderRepository.findOrderByCustomerName(customerName);
    }

    @GetMapping("/warehouses")
    public List<Warehouse> findWarehouses() {
        return warehouseRepository.findAll();
    }

    @PostMapping("/customer")
    public Customer saveNewCustomer(@RequestBody Customer customer) {
        return customerRepository.insert(customer);
    }

    @PostMapping("/order")
    public Order makeOrder(@RequestBody MakeOrderDTO makeOrderDTO) {
        Customer customer = customerRepository.findById(makeOrderDTO.getCustomerDTO().getName()).
                orElseThrow(() -> new IllegalArgumentException("no such customer"));
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

        return orderRepository.insert(finalOrder);
    }
}
