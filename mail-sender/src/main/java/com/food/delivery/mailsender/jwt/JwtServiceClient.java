package com.food.delivery.mailsender.jwt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient("jwt-service")
public interface JwtServiceClient {

	@GetMapping("/jwt/account-activate")
	Mono<String> getAccountActivateJwt(@RequestParam String oktaUserId);
}
