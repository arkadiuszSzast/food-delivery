package com.food.delivery.companyservice.config;

import com.food.delivery.companyservice.CompanyServiceApplication;
import org.springframework.context.annotation.Configuration;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@Configuration
@EnableReactiveFeignClients(basePackageClasses = CompanyServiceApplication.class)
public class FeignConfiguration {
}
