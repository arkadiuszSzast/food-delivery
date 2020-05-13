package com.food.delivery.gateway.config.swagger;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import springfox.documentation.swagger.web.SwaggerResource;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
final class SwaggerResourceBuilder {

	private static final String SWAGGER_VERSION = "2.0";

	static SwaggerResource aSwaggerResource(String name, String location) {
		final var swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setLocation(location);
		swaggerResource.setSwaggerVersion(SWAGGER_VERSION);
		return swaggerResource;
	}

}
