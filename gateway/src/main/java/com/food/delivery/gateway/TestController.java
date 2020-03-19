package com.food.delivery.gateway;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestController {

	@GetMapping("/test")
	public Mono<String> test(OAuth2AuthenticationToken token) {
		return Mono.just("WORKING");
	}
}
