package com.logistic.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LogisticController {
    @GetMapping("/greet")
    public String greet() {
        return "Hello";
    }

    public Map<String, String> getAllWarehouses() {

    }
}
