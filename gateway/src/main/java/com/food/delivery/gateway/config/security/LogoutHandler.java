package com.food.delivery.gateway.config.security;

import com.food.delivery.gateway.token.BlacklistedTokenCreateService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import reactor.core.publisher.Mono;

@Configuration
@AllArgsConstructor
public class LogoutHandler implements ServerLogoutHandler {

	private final BlacklistedTokenCreateService blacklistedTokenCreateService;

	@Override
	public Mono<Void> logout(WebFilterExchange exchange, Authentication authentication) {
		final var jwt = ((JwtAuthenticationToken) authentication).getToken();
		return blacklistedTokenCreateService.create(jwt).then(Mono.empty());
	}
}
