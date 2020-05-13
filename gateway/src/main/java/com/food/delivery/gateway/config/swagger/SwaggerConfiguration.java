package com.food.delivery.gateway.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Primary
@Profile("swagger")
@Configuration
@EnableSwagger2WebFlux
public class SwaggerConfiguration implements SwaggerResourcesProvider {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(Predicate.not(PathSelectors.regex("/actuator.*")))
				.build()
				.genericModelSubstitutes(Optional.class);
	}

	@Override
	public List<SwaggerResource> get() {

		final var swaggerResourceAccount = SwaggerResourceBuilder
				.aSwaggerResource("account-service", "/account-service/v2/api-docs");
		final var swaggerResourceCompany = SwaggerResourceBuilder
				.aSwaggerResource("company-service", "/company-service/v2/api-docs");
		final var swaggerResourceOktaAdapter = SwaggerResourceBuilder
				.aSwaggerResource("okta-adapter", "/okta-adapter/v2/api-docs");

		return List.of(swaggerResourceAccount, swaggerResourceCompany, swaggerResourceOktaAdapter);
	}
}
