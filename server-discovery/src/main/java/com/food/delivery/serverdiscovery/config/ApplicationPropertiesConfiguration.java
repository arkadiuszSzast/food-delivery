package com.food.delivery.serverdiscovery.config;

import com.food.delivery.serverdiscovery.utils.properties.ActuatorProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ActuatorProperties.class)
public class ApplicationPropertiesConfiguration {
}
