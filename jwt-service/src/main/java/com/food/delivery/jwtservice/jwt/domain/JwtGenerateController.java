package com.food.delivery.jwtservice.jwt.domain;

import com.food.delivery.jwtservice.jwt.JwtGenerateService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/jwt")
public class JwtGenerateController {

	private final JwtGenerateService jwtGenerateService;

	@GetMapping("/user-activate")
	public Mono<String> getUserActivateJwt(@RequestParam String oktaUserId) {
		return Mono.just(jwtGenerateService.getUserActivateJwt(oktaUserId));
	}

	@GetMapping("/employee-activate")
	public Mono<String> getEmployeeActivateJwt(@RequestParam String oktaUserId) {
		return Mono.just(jwtGenerateService.getEmployeeActivateJwt(oktaUserId));
	}

	@GetMapping("/company-admin-activate")
	public Mono<String> getCompanyAdminActivateJwt(@RequestParam String oktaUserId) {
		return Mono.just(jwtGenerateService.getCompanyAdminActivateJwt(oktaUserId));
	}

	@GetMapping("/company-admin-register")
	public Mono<String> getCompanyAdminRegisterJwt(@RequestParam String email) {
		return Mono.just(jwtGenerateService.getCompanyAdminRegisterJwt(email));
	}
}
