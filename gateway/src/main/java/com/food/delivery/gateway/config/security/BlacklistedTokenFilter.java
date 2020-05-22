package com.food.delivery.gateway.config.security;

import com.food.delivery.gateway.token.BlacklistedTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

@Component
@AllArgsConstructor
public class BlacklistedTokenFilter implements WebFilter {

	private final BlacklistedTokenRepository blacklistedTokenRepository;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		return getAuthorization(exchange.getRequest())
				.flatMap(blacklistedTokenRepository::findByToken)
				.flatMap(token -> Mono.error(new AuthorizationServiceException(
						String.format("Token: %s is blacklisted", token)))
				);
	}

	private Mono<String> getAuthorization(ServerHttpRequest request) {
		String authorization = request.getHeaders().getFirst("Authorization");
		Predicate<String> isBearerToken = auth -> auth.contains("Bearer");
		return Mono.justOrEmpty(authorization)
				.filter(isBearerToken)
				.map(this::getToken);

	}

	private String getToken(String authHeader) {
		final var authHeaderParts = authHeader.split(" ");
		if (authHeaderParts.length != 2) {
			throw new IllegalArgumentException();
		}
		return authHeaderParts[1];
	}
}
