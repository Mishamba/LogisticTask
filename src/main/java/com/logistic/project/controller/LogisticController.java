package com.logistic.project.controller;

import com.logistic.project.model.Customer;
import com.logistic.project.model.Order;
import com.logistic.project.model.Warehouse;
import com.logistic.project.model.dto.MakeOrderDTO;
import com.logistic.project.repository.CustomerRepository;
import com.logistic.project.repository.WarehouseRepository;
import com.logistic.project.util.WarehouseCustomerDistanceComparator;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class LogisticController {
    private final CustomerRepository customerRepository;
    private final WarehouseRepository warehouseRepository;

    @PostMapping
    public Customer createNewCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Order getOrderRouteAndCost(MakeOrderDTO makeOrderDTO) {
        Customer customer = customerRepository.findCustomerByName(makeOrderDTO.getCustomerDTO().getName());

        Order finalOrder = Order.builder().
                warehouses(null).
                customer(customer).
                merchandiseQuantity(makeOrderDTO.getMerchandiseQuantity()).
                build();

        for (String merchandiseName : makeOrderDTO.getMerchandiseQuantity().keySet()) {
            List<Warehouse> warehouses = warehouseRepository.findWarehousesByMerchandiseQuantityContaining(merchandiseName);
            if (warehouses != null ) {
                continue;
            }
            warehouses.sort(new WarehouseCustomerDistanceComparator(customer));

            int requiredMerchandiseQuantity = makeOrderDTO.getMerchandiseQuantity().get(merchandiseName);
            for (Warehouse warehouse : warehouses) {
                requiredMerchandiseQuantity -= warehouse.getMerchandiseQuantity().get(merchandiseName);
                if (requiredMerchandiseQuantity <= 0) {
                    break;
                } else {
                    finalOrder.addWarehouse(warehouse);
                }
            }
        }

        return finalOrder;
    }
}
