package com.food.delivery.gateway.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Profile("!swagger")
@RestController
public class DisableSwaggerUiController {

	@Bean
	public RouterFunction<ServerResponse> notFound() {
		return RouterFunctions
				.route(GET("/swagger-ui.html"),
						request -> ServerResponse.notFound().build());
	}
}
