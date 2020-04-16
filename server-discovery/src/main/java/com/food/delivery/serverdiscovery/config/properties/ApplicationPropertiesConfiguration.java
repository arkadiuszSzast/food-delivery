package com.food.delivery.serverdiscovery.config.properties;

import com.food.delivery.serverdiscovery.utils.properties.ActuatorProperties;
import com.food.delivery.serverdiscovery.utils.properties.DashboardProperties;
import com.food.delivery.serverdiscovery.utils.properties.EurekaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ActuatorProperties.class, EurekaProperties.class, DashboardProperties.class})
public class ApplicationPropertiesConfiguration {
}
