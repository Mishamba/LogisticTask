package com.logistic.project.controller;

import com.logistic.project.model.Customer;
import com.logistic.project.model.Order;
import com.logistic.project.model.Warehouse;
import com.logistic.project.model.dto.MakeOrderDTO;
import com.logistic.project.repository.CustomerRepository;
import com.logistic.project.repository.WarehouseRepository;
import com.logistic.project.util.WarehouseCustomerDistanceComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LogisticController {
    private final CustomerRepository customerRepository;
    private final WarehouseRepository warehouseRepository;

    @Autowired
    public LogisticController(CustomerRepository customerRepository, WarehouseRepository warehouseRepository) {
        this.customerRepository = customerRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @PostMapping
    public String createNewCustomer(Customer customer) {
        customerRepository.save(customer);
        return "response:200";
    }

    public Order getOrderRouteAndCost(MakeOrderDTO makeOrderDTO) {
        Customer customer = customerRepository.findCustomerByName(makeOrderDTO.getCustomerDTO().getName());

        List<Warehouse> warehouseList = new ArrayList<>();
        for (String merchandiseName : makeOrderDTO.getMerchandiseQuantity().keySet()) {
            Warehouse warehouse = warehouseRepository.findWarehouseByMerchandiseQuantityContaining(merchandiseName);
            if (warehouse != null ) {
                warehouseList.add(warehouse);
            }
        }

        warehouseList.sort(new WarehouseCustomerDistanceComparator(customer));

        // TODO: 12/27/21 get merchandise from nearest warehouses, calculate cost, store in ComplitedOrderDTO and send
    }
}
