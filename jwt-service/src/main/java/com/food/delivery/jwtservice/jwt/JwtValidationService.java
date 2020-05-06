package com.food.delivery.jwtservice.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.food.delivery.jwtservice.utils.properties.jwt.JwtProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class JwtValidationService {

	private final JwtProperties jwtProperties;

	public Mono<String> validateActivateAccountToken(String token) {
		final var secret = jwtProperties.getActivateAccount().getSecret();
		return Mono.just(JWT.require(Algorithm.HMAC256(secret)).build())
				.map(jwtVerifier -> jwtVerifier.verify(token))
				.map(DecodedJWT::getSubject);
	}
}
