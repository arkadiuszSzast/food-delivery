package com.food.delivery.configserver.config;

import com.food.delivery.configserver.utils.properties.ActuatorProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ActuatorProperties.class)
public class ApplicationPropertiesConfiguration {
}
