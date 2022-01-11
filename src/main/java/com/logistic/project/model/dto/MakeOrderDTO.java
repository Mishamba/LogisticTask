package com.logistic.project.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class MakeOrderDTO {
    @JsonProperty("customer")
    private CustomerDTO customerDTO;
    @JsonProperty("merchandiseQuantity")
    private Map<String, Integer> merchandiseQuantity;
}
