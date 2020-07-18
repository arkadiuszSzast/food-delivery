package com.food.delivery.menuservice.config;

import com.food.delivery.menuservice.utils.properties.ActuatorProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ActuatorProperties.class)
public class ApplicationPropertiesConfiguration {
}
