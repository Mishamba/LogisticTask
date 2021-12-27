package com.logistic.project.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class LogisticMap {
    @Id
    private String id;

    @Field(name = "routes")
    List<Route> routeList;

    public void addRoute(Route routeToAdd) {
        routeList.add(routeToAdd);
    }
}
