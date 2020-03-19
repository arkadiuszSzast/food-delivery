package com.food.delivery.accountservice;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestController {

	@GetMapping("/test/account")
	public Mono<String> test(@AuthenticationPrincipal JwtAuthenticationToken principal) {
		return Mono.just("HELLO FROM ACCOUNT");
	}
}
