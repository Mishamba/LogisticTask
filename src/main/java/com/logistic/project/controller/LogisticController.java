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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
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
    @Cacheable(value = "List<Order>", key = "{#customerName + 's_orders', #page, #size}")
    public List<Order> findOrders(@RequestParam("customerName") String customerName,
                                  @RequestParam(name = "page") int page,
                                  @RequestParam(name = "size") int size) {
        return orderRepository.findOrderByCustomerName(customerName, PageRequest.of(page, size)).getContent();
    }

    @GetMapping("/warehouses")
    @Cacheable(value = "List<Warehouse>", key = "{'warehouses_' + #page + '_' + #size}")
    public List<Warehouse> findWarehouses(@RequestParam("page") int page, @RequestParam("size") int size) {
        return warehouseRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    @PostMapping("/customer")
    public Customer saveNewCustomer(@RequestBody Customer customer) {
        return customerRepository.insert(customer);
    }

    @PostMapping("/order")
    @CacheEvict(value = "List<Order>", allEntries = true)
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
        finalOrder.setDistance(CoordinateCalculator.
                getCoordinateDistance(customer.getPosition(), finalWarehouse.getPosition()));

        return orderRepository.insert(finalOrder);
    }
}
