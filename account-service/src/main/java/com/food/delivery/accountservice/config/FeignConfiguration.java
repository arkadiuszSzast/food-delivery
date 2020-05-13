package com.food.delivery.accountservice.config;

import com.food.delivery.accountservice.AccountServiceApplication;
import org.springframework.context.annotation.Configuration;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@Configuration
@EnableReactiveFeignClients(basePackageClasses = AccountServiceApplication.class)
public class FeignConfiguration {
}
