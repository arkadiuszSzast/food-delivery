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

	@GetMapping("/account-activate")
	public Mono<String> getAccountActivateJwt(@RequestParam String oktaUserId) {
		return Mono.just(jwtGenerateService.getAccountActivateJwt(oktaUserId));
	}
}
