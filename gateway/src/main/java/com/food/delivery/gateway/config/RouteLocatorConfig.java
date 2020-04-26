package com.food.delivery.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteLocatorConfig {

	public static final String SWAGGER_EP = "/v2/api-docs";
	private static final String LB_ACCOUNT_SERVICE = "lb://account-service";
	private static final String LB_COMPANY_SERVICE = "lb://company-service";
	private static final String LB_OKTA_ADAPTER = "lb://okta-adapter";

	@Bean
	public RouteLocator swaggerRouter(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("account-service-swagger", r -> r.path("/account-service/v2/api-docs")
						.filters(f -> f.rewritePath("/account-service/v2/api-docs", SWAGGER_EP))
						.uri(LB_ACCOUNT_SERVICE))
				.route("company-service-swagger", r -> r.path("/company-service/v2/api-docs")
						.filters(f -> f.rewritePath("/company-service/v2/api-docs", SWAGGER_EP))
						.uri(LB_COMPANY_SERVICE))
				.route("okta-adapter-swagger", r -> r.path("/okta-adapter/v2/api-docs")
						.filters(f -> f.rewritePath("/okta-adapter/v2/api-docs", SWAGGER_EP))
						.uri(LB_OKTA_ADAPTER))
				.build();
	}

	@Bean
	public RouteLocator accountServiceRouter(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("account-service-accounts", r -> r.path("/account")
						.uri(LB_ACCOUNT_SERVICE))
				.route("account-service-me", r -> r.path("/account/me")
						.uri(LB_ACCOUNT_SERVICE))
				.build();
	}

	@Bean
	public RouteLocator companyServiceRouter(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("company-service-companies", r -> r.path("/company")
						.uri(LB_COMPANY_SERVICE))
				.build();
	}
}
