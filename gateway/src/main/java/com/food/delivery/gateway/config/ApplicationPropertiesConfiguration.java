package com.food.delivery.gateway.config;

import com.food.delivery.gateway.utils.properties.ActuatorProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ActuatorProperties.class)
public class ApplicationPropertiesConfiguration {
}
