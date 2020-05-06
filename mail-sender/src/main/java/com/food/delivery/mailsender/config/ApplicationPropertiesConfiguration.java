package com.food.delivery.mailsender.config;

import com.food.delivery.mailsender.utils.properties.ActuatorProperties;
import com.food.delivery.mailsender.utils.properties.SendgridProperties;
import com.food.delivery.mailsender.utils.properties.SendgridTemplatesProperties;
import com.food.delivery.mailsender.utils.properties.UrlsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ActuatorProperties.class, SendgridTemplatesProperties.class,
		SendgridProperties.class, UrlsProperties.class})
public class ApplicationPropertiesConfiguration {
}
