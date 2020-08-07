package com.food.delivery.gateway.config.swagger;

import lombok.AllArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("swagger")
@AllArgsConstructor
public class SwaggerConfiguration {

	private final RouteDefinitionLocator routeDefinitionLocator;

	@Bean
	public List<GroupedOpenApi> apis() {
		return routeDefinitionLocator.getRouteDefinitions()
				.filter(routeDefinition -> routeDefinition.getId().matches(".*-service-docs|.*-adapter-docs"))
				.map(routeDefinition -> routeDefinition.getId().replace("-docs", ""))
				.map(serviceId -> GroupedOpenApi.builder()
						.pathsToMatch("/" + serviceId + "/**")
						.setGroup(serviceId).build())
				.collectList()
				.block();
	}
}
