package com.food.delivery.accountservice.jwt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(value = "jwt-service", fallbackFactory = JwtClientFallbackFactory.class)
public interface JwtServiceClient {

	@GetMapping("/jwt/validations/user-activate")
	Mono<String> validateUserActivateToken(@RequestParam String token);

	@GetMapping("/jwt/validations/employee-activate")
	Mono<String> validateEmployeeActivateToken(@RequestParam String token);

	@GetMapping("/jwt/validations/company-admin-activate")
	Mono<String> validateCompanyAdminActivateToken(@RequestParam String token);

	@GetMapping("/jwt/validations/company-admin-register")
	Mono<String> validateCompanyAdminRegisterToken(@RequestParam String token);
}
