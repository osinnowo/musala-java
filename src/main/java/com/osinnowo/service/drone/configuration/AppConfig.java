package com.osinnowo.service.drone.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AppConfig {

    @Value("${app.drone.weight.limit}")
    private Double weightLimit;
}