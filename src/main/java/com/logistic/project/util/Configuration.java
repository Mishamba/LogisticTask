package com.logistic.project.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:LogicConfiguration.properties")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Configuration {
    @Value("${mile.cost}")
    private static Double mileCost;
}
