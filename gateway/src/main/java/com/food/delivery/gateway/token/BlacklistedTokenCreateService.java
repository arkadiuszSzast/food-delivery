package com.food.delivery.gateway.token;

import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class BlacklistedTokenCreateService {

	private final BlacklistedTokenRepository blacklistedTokenRepository;

	public Mono<BlacklistedToken> create(Jwt jwt) {
		final var token = jwt.getTokenValue();
		final var blacklistedToken = new BlacklistedToken(token);
		return blacklistedTokenRepository.save(blacklistedToken);
	}
}
