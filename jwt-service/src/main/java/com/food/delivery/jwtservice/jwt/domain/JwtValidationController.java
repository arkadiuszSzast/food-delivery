package com.food.delivery.jwtservice.jwt.domain;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.food.delivery.jwtservice.jwt.JwtValidationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/jwt/validations")
public class JwtValidationController {

	private final JwtValidationService jwtValidationService;

	@GetMapping("/user-activate")
	public Mono<String> validateUserActivateJwt(@RequestParam String token) {
		return jwtValidationService.validateActivateUserToken(token);
	}

	@GetMapping("/employee-activate")
	public Mono<String> validateEmployeeActivateJwt(@RequestParam String token) {
		return jwtValidationService.validateActivateEmployeeToken(token);
	}

	@GetMapping("/company-admin-activate")
	public Mono<String> validateCompanyAdminActivateJwt(@RequestParam String token) {
		return jwtValidationService.validateActivateCompanyAdminToken(token);
	}

	@GetMapping("/company-admin-register")
	public Mono<String> validateCompanyAdminRegisterJwt(@RequestParam String token) {
		return jwtValidationService.validateCompanyAdminRegisterToken(token);
	}

	@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Given token is invalid")
	@ExceptionHandler(JWTVerificationException.class)
	public void jwtVerificationExceptionHandler() {
		log.warn("Invalid token provided");
	}
}
