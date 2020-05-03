package com.food.delivery.mailsender.config;

import com.food.delivery.mailsender.utils.properties.ActuatorProperties;
import com.food.delivery.mailsender.utils.properties.SendGridTemplatesProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ActuatorProperties.class, SendGridTemplatesProperties.class})
public class ApplicationPropertiesConfiguration {
}
