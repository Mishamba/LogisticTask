package com.logistic.project.configuration;

import com.logistic.project.model.Customer;
import com.logistic.project.model.Warehouse;
import com.logistic.project.repository.CustomerRepository;
import com.logistic.project.repository.WarehouseRepository;
import com.logistic.project.util.Coordinate;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class DemoDataWriter implements ApplicationRunner {
    private WarehouseRepository warehouseRepository;
    private CustomerRepository customerRepository;

    @Override
    public void run(ApplicationArguments args) {
        customerRepository.deleteAll();
        warehouseRepository.deleteAll();

        Customer customer1 = new Customer("Gleb", new Coordinate(4, 3));
        Customer customer2 = new Customer("Sasha", new Coordinate(8, 9));
        Customer customer3 = new Customer("Misha", new Coordinate(15, 10));

        Map<String, Integer> merchandiseQuantity1 = new HashMap<>();
        merchandiseQuantity1.put("computer", 16);
        merchandiseQuantity1.put("bebra", 6);
        merchandiseQuantity1.put("vacine", 10);
        Map<String, Integer> merchandiseQuantity2 = new HashMap<>();
        merchandiseQuantity2.put("laptop", 100);
        merchandiseQuantity2.put("grivna", 20);
        merchandiseQuantity2.put("beer", 1);
        Map<String, Integer> merchandiseQuantity3 = new HashMap<>();
        merchandiseQuantity3.put("cup", 13);
        merchandiseQuantity3.put("chair", 90);
        merchandiseQuantity3.put("notebook", 18);
        Map<String, Integer> merchandiseQuantity4 = new HashMap<>();
        merchandiseQuantity4.put("gun", 54);
        merchandiseQuantity4.put("answer", 42);
        merchandiseQuantity4.put("computer", 4);
        Map<String, Integer> merchandiseQuantity5 = new HashMap<>();
        merchandiseQuantity5.put("gun", 16);
        merchandiseQuantity5.put("grinva", 6);
        merchandiseQuantity5.put("charger", 132);
        Map<String, Integer> merchandiseQuantity6 = new HashMap<>();
        merchandiseQuantity6.put("computer", 16);
        merchandiseQuantity6.put("bebra", 6);
        merchandiseQuantity6.put("vacine", 10);
        Warehouse warehouse1 = new Warehouse("Compluter Inc", merchandiseQuantity1, new Coordinate(43,12));
        Warehouse warehouse2 = new Warehouse("Bebra", merchandiseQuantity2, new Coordinate(21, 89));
        Warehouse warehouse3 = new Warehouse("LG", merchandiseQuantity3, new Coordinate(15, 90));
        Warehouse warehouse4 = new Warehouse("Abchihba", merchandiseQuantity4, new Coordinate(567, 890));
        Warehouse warehouse5 = new Warehouse("Node", merchandiseQuantity5, new Coordinate(389, 54));
        Warehouse warehouse6 = new Warehouse("Meta", merchandiseQuantity6, new Coordinate(321, 590));

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        warehouseRepository.save(warehouse1);
        warehouseRepository.save(warehouse2);
        warehouseRepository.save(warehouse3);
        warehouseRepository.save(warehouse4);
        warehouseRepository.save(warehouse5);
        warehouseRepository.save(warehouse6);
    }
}
