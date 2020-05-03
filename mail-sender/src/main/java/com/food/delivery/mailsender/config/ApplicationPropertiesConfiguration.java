package com.food.delivery.mailsender.config;

import com.food.delivery.mailsender.utils.properties.ActuatorProperties;
import com.food.delivery.mailsender.utils.properties.SendgridProperties;
import com.food.delivery.mailsender.utils.properties.SendgridTemplatesProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ActuatorProperties.class, SendgridTemplatesProperties.class, SendgridProperties.class})
public class ApplicationPropertiesConfiguration {
}
