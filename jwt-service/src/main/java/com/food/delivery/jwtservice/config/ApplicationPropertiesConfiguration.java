package com.food.delivery.jwtservice.config;

import com.food.delivery.jwtservice.utils.properties.ActuatorProperties;
import com.food.delivery.jwtservice.utils.properties.jwt.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ActuatorProperties.class, JwtProperties.class})
public class ApplicationPropertiesConfiguration {
}
