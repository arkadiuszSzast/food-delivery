package com.food.delivery.companyservice.config;

import com.food.delivery.companyservice.utils.properties.ActuatorProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ActuatorProperties.class)
public class ApplicationPropertiesConfiguration {
}
