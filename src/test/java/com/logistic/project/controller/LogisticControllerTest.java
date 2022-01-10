package com.logistic.project.controller;

import com.logistic.project.model.Customer;
import com.logistic.project.model.dto.CustomerDTO;
import com.logistic.project.model.dto.MakeOrderDTO;
import com.logistic.project.util.Coordinate;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;

@SpringBootTest
@Testcontainers
@AutoConfigureWebTestClient
class LogisticControllerTest {
    @ClassRule
    private final static GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:latest")).
            withExposedPorts(6379).
            withCommand("redis-server --requirepass 123");

    @ClassRule
    private final static GenericContainer mongo = new GenericContainer(DockerImageName.parse("mongo:latest")).
            withExposedPorts(27017);

    @BeforeAll
    static void startContainers() {
        redis.start();
        mongo.start();
    }

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void findOrders() {
       this.webTestClient.
               get().
               uri("/orders?page=0&size=1").
               header("accept", "application/json").
               exchange().
               expectStatus().
               is2xxSuccessful().
               expectHeader().
               contentType("application/json").
               expectBody().
               jsonPath("[0].customer").
               isNotEmpty().
               jsonPath("[0].distance").
               isNotEmpty().
               jsonPath("[0].warehouse").
               isNotEmpty();
    }

    @Test
    void findWarehouses() {
        this.webTestClient.
                get().
                uri("/warehouses?page=0&size=1").
                header("accept", "application/json").
                exchange().
                expectStatus().
                is2xxSuccessful().
                expectHeader().
                contentType("application/json").
                expectBody().
                jsonPath("[0].name").
                isNotEmpty().
                jsonPath("[0].merchandiseQuantity").
                isNotEmpty().
                jsonPath("[0].position").
                isNotEmpty();
    }

    @Test
    void saveNewCustomer() {
        this.webTestClient.
                post().
                uri("/customer").
                header("Content-Type", "application/json").
                body(BodyInserters.fromValue(Customer.builder().name("smth").position(new Coordinate(1, 2)))).
                exchange().
                expectStatus().
                is2xxSuccessful().
                expectHeader().
                contentType("application/json").
                expectBody().
                jsonPath("name").
                isNotEmpty().
                jsonPath("position").
                isNotEmpty();
    }

    @Test
    void makeOrder() {
        HashMap<String, Integer> merchandiseQuantity = new HashMap<>();
        merchandiseQuantity.put("computer", 1);
        this.webTestClient.
                post().
                uri("/order").
                header("Content-Type", "application/json").
                body(BodyInserters.fromValue(MakeOrderDTO.builder().customerDTO(new CustomerDTO("smth")).merchandiseQuantity(merchandiseQuantity))).
                exchange().
                expectStatus().
                is2xxSuccessful().
                expectHeader().
                contentType("application/json").
                expectBody().
                jsonPath("id").
                isNotEmpty().
                jsonPath("customer").
                isNotEmpty().
                jsonPath("distance").
                isNotEmpty().
                jsonPath("warehouse").
                isNotEmpty();
    }
}