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

	private static final String LB_ACCOUNT_SERVICE = "lb://account-service";
	private static final String LB_COMPANY_SERVICE = "lb://company-service";

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder,
										   TokenRelayGatewayFilterFactory filterFactory) {
		return builder.routes()
				.route("account-service-swagger", r -> r.path("/account-service/v2/api-docs")
						.filters(f -> f.rewritePath("/account-service/v2/api-docs", "/v2/api-docs"))
						.uri(LB_ACCOUNT_SERVICE))
				.route("company-service-swagger", r -> r.path("/company-service/v2/api-docs")
						.filters(f -> f.rewritePath("/company-service/v2/api-docs", "/v2/api-docs"))
						.uri(LB_COMPANY_SERVICE))
				.route("account-service-accounts", r -> r.path("/account")
						.uri(LB_ACCOUNT_SERVICE))
				.route("account-service-me", r -> r.path("/account/me")
						.uri(LB_ACCOUNT_SERVICE))
				.route("company-service-companies", r -> r.path("/company")
						.uri(LB_COMPANY_SERVICE))
				.build();
	}
}
