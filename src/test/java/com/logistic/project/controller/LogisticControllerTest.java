package com.logistic.project.controller;

import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class LogisticControllerTest {
    @ClassRule
    private DockerComposeContainer compose = new DockerComposeContainer(
            new File("test/resources/dockerCompose.yml"));

    @Test
    void findOrders() {
        String request = "http://" + compose.getServiceHost("service_l", 8080) + ":" +
                compose.getServicePort("service_l", 8080) +
                "/orders?customerName=Misha&page=2&size=3";
        try {
            String actualResponse = simpleGetRequest(request);
            String expectedResponse = readFile("findOrders.json");
            assertEquals(expectedResponse, actualResponse);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void findWarehouses() {
        String request = "http://" + compose.getServiceHost("service_l", 8080) + ":" +
                compose.getServicePort("service_l", 8080) +
                "/warehouses?page=2&size=3";
        try {
            String actualResponse = simpleGetRequest(request);
            String expectedResponse = readFile("findWarehouses.json");
            assertEquals(expectedResponse, actualResponse);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    // TODO: 1/6/22 finish two methods, add response files, add service_l container to docker-compose

    @Test
    void saveNewCustomer() {
    }

    @Test
    void makeOrder() {
    }

    private static String readFile(String fileName) throws IOException {
        return Files.readString(Paths.get("test/resources/responses/" + fileName));
    }

    private String simpleGetRequest(String address) throws Exception {
        URL url = new URL(address);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        return content.toString();
    }
}