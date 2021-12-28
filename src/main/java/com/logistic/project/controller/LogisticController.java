package com.logistic.project.controller;

import com.logistic.project.model.Customer;
import com.logistic.project.model.Order;
import com.logistic.project.model.Warehouse;
import com.logistic.project.model.dto.MakeOrderDTO;
import com.logistic.project.repository.CustomerRepository;
import com.logistic.project.repository.WarehouseRepository;
import com.logistic.project.util.Configuration;
import com.logistic.project.util.CoordinateCalculator;
import com.logistic.project.util.WarehouseCustomerDistanceComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

        Order finalOrder = new Order(null, customer, makeOrderDTO.getMerchandiseQuantity(), null);

        for (String merchandiseName : makeOrderDTO.getMerchandiseQuantity().keySet()) {
            List<Warehouse> warehouses = warehouseRepository.findWarehousesByMerchandiseQuantityContaining(merchandiseName);
            if (warehouses != null ) {
                continue;
            }
            // TODO: 12/28/21 fix warning
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

        double cost = 0;
        for (Warehouse warehouse : finalOrder.getWarehouses()) {
            double merchandiseDeliveryCost = CoordinateCalculator.getCoordinateDistance(customer.getPosition(), warehouse.getPosition()) *
                    Configuration.MILE_COST;
            for (String merchandiseName : makeOrderDTO.getMerchandiseQuantity().keySet()) {
                int merchandiseQuantityToDeliver = makeOrderDTO.getMerchandiseQuantity().get(merchandiseName) -
                        warehouse.getMerchandiseQuantity().get(merchandiseName);
                cost += merchandiseDeliveryCost * merchandiseQuantityToDeliver;
                int leftMerchandiseToDeliver = Math.max(merchandiseQuantityToDeliver, 0);
                if (leftMerchandiseToDeliver > 0) {
                    makeOrderDTO.getMerchandiseQuantity().put(merchandiseName, leftMerchandiseToDeliver);
                } else {
                    makeOrderDTO.getMerchandiseQuantity().remove(merchandiseName);
                }
            }
        }

        finalOrder.setCost(cost);

        return finalOrder;
    }
}
