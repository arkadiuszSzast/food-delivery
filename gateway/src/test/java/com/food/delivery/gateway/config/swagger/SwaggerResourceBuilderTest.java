package com.food.delivery.gateway.config.swagger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class SwaggerResourceBuilderTest {

	private static final String SWAGGER_VERSION = "2.0";
	private static final String SWAGGER_RESOURCE_NAME = "swagger-resource-name";
	private static final String SWAGGER_RESOURCE_LOCATION = "swagger-resource-location";

	@Test
	@DisplayName("Should create instance of swagger resource")
	void shouldCreateSwaggerResource() {
		final var swaggerResource = SwaggerResourceBuilder
				.aSwaggerResource(SWAGGER_RESOURCE_NAME, SWAGGER_RESOURCE_LOCATION);

		assertAll(
				() -> assertThat(swaggerResource.getName()).isEqualTo(SWAGGER_RESOURCE_NAME),
				() -> assertThat(swaggerResource.getUrl()).isEqualTo(SWAGGER_RESOURCE_LOCATION),
				() -> assertThat(swaggerResource.getSwaggerVersion()).isEqualTo(SWAGGER_VERSION)
		);
	}

}
