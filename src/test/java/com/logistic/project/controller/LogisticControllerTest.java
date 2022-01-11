package com.logistic.project.controller;

import com.logistic.project.model.Customer;
import com.logistic.project.model.dto.CustomerDTO;
import com.logistic.project.model.dto.MakeOrderDTO;
import com.logistic.project.util.Coordinate;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ExtendWith(SpringExtension.class)
class LogisticControllerTest {
    @ClassRule
    private final static GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:latest")).
            withExposedPorts(6379).
            withCommand("redis-server --requirepass 123");

    @Container
    final static MongoDBContainer mongo = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        mongo.start();
        redis.start();
        registry.add("spring.data.mongodb.host", mongo::getHost);
        registry.add("spring.data.mongodb.port", mongo::getFirstMappedPort);
        registry.add("spring.redis.host", redis::getHost);
        registry.add("spring.redis.port", redis::getFirstMappedPort);
    }

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void findOrders() {
       String customerName = "Misha";
       this.webTestClient
               .get()
               .uri(uriBuilder -> uriBuilder
                       .path("/orders")
                       .queryParam("customerName", customerName)
                       .queryParam("page", 0)
                       .queryParam("size", 1)
                       .build())
               .header("accept", "application/json")
               .exchange()
               .expectStatus()
               .is2xxSuccessful()
               .expectHeader()
               .contentType("application/json")
               .expectBody()
               .jsonPath("[0]")
               .isNotEmpty()
               .jsonPath("[0].customer.name")
               .isEqualTo(customerName)
               .jsonPath("[0].distance")
               .isNotEmpty()
               .jsonPath("[0].warehouse")
               .isNotEmpty();
    }

    @Test
    void findWarehouses() {
        this.webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/warehouses")
                        .queryParam("page", 0)
                        .queryParam("size", 1)
                        .build())
                .header("accept", "application/json")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType("application/json")
                .expectBody()
                .jsonPath("[0].name")
                .isNotEmpty()
                .jsonPath("[0].merchandiseQuantity")
                .isNotEmpty()
                .jsonPath("[0].position")
                .isNotEmpty();
    }

    @Test
    void saveNewCustomer() {
        this.webTestClient
                .post()
                .uri("/customer")
                .header("Content-Type", "application/json")
                .bodyValue(Customer.builder().name("smth").position(new Coordinate(1, 2)).build())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType("application/json") .expectBody()
                .jsonPath("name")
                .isNotEmpty()
                .jsonPath("position")
                .isNotEmpty();
    }

    @Test
    void makeOrder() {
        HashMap<String, Integer> merchandiseQuantity = new HashMap<>();
        merchandiseQuantity.put("computer", 1);
        this.webTestClient
                .post()
                .uri("/order")
                .header("Content-Type", "application/json")
                .bodyValue(MakeOrderDTO.builder().customerDTO(new CustomerDTO("Misha")).merchandiseQuantity(merchandiseQuantity).build())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType("application/json")
                .expectBody()
                .jsonPath("id")
                .isNotEmpty()
                .jsonPath("customer")
                .isNotEmpty()
                .jsonPath("distance")
                .isNotEmpty()
                .jsonPath("warehouse")
                .isNotEmpty();
    }
}