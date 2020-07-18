package com.food.delivery.menuservice.config;

import com.food.delivery.menuservice.MenuServiceApplication;
import org.springframework.context.annotation.Configuration;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@Configuration
@EnableReactiveFeignClients(basePackageClasses = MenuServiceApplication.class)
public class FeignConfiguration {
}
