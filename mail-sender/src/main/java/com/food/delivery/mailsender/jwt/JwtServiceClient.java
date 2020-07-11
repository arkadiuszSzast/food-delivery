package com.food.delivery.mailsender.jwt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(value = "jwt-service", fallbackFactory = JwtClientFallbackFactory.class)
public interface JwtServiceClient {

	@GetMapping("/jwt/user-activate")
	Mono<String> getUserActivateJwt(@RequestParam String oktaUserId);

	@GetMapping("/jwt/employee-activate")
	Mono<String> getEmployeeActivateJwt(@RequestParam String oktaUserId);

	@GetMapping("/jwt/company-admin-activate")
	Mono<String> getCompanyAdminActivateJwt(@RequestParam String oktaUserId);

	@GetMapping("/jwt/company-admin-register")
	Mono<String> getCompanyAdminRegisterJwt(@RequestParam String email);
}
