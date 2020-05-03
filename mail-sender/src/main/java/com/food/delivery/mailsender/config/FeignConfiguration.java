package com.food.delivery.mailsender.config;

import com.food.delivery.mailsender.MailSenderApplication;
import org.springframework.context.annotation.Configuration;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@Configuration
@EnableReactiveFeignClients(basePackageClasses = MailSenderApplication.class)
public class FeignConfiguration {
}
