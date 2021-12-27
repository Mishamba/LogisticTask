package com.logistic.project.model;

import lombok.Data;

import java.util.List;

@Data
public class LogisticMap {
    List<Route> routeList;

    public void addRoute(Route routeToAdd) {
        routeList.add(routeToAdd);
    }
}
