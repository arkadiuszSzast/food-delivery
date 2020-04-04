package com.food.delivery.oktaadapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class OktaAdapterApplication {

	public static void main(String[] args) {
		SpringApplication.run(OktaAdapterApplication.class, args);
	}

}
