package com.food.delivery.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

import java.util.List;
import java.util.Optional;

@Primary
@Configuration
@EnableSwagger2WebFlux
public class SwaggerConfig implements SwaggerResourcesProvider {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.genericModelSubstitutes(Optional.class);
	}

	@Override
	public List<SwaggerResource> get() {

		var swaggerResourceAccount = new SwaggerResource();
		swaggerResourceAccount.setName("account-service");
		swaggerResourceAccount.setLocation("/account-service/v2/api-docs");
		swaggerResourceAccount.setSwaggerVersion("2.0");

		return List.of(swaggerResourceAccount);
	}
}
