package com.logistic.project.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:LogicConfiguration.properties")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Configuration {
    @Value("${mile.cost}")
    public static Double MILE_COST;
}
