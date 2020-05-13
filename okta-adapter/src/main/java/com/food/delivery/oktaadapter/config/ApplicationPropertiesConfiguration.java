package com.food.delivery.oktaadapter.config;

import com.food.delivery.oktaadapter.utils.properties.ActuatorProperties;
import com.food.delivery.oktaadapter.utils.properties.OktaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({OktaProperties.class, ActuatorProperties.class})
public class ApplicationPropertiesConfiguration {
}
