package com.food.delivery.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.security.oauth2.gateway.TokenRelayGatewayFilterFactory;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder,
										   TokenRelayGatewayFilterFactory filterFactory) {
		return builder.routes()
				.route("account-service", r -> r.path("/account")
						.filters(f -> f.filter(filterFactory.apply()))
						.uri("lb://account-service"))
				.route("account-service", r -> r.path("/account-service/v2/api-docs")
						.filters(f -> f.rewritePath("/account-service/v2/api-docs", "/v2/api-docs"))
						.uri("lb://account-service"))
				.build();
	}
}
