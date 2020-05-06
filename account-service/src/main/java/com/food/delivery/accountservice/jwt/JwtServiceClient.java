package com.food.delivery.accountservice.jwt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient("jwt-service")
public interface JwtServiceClient {

	@GetMapping("/jwt/validations/account-activate")
	Mono<String> validateAccountActivateToken(@RequestParam String token);
}
