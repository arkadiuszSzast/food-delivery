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

	public Mono<String> validateActivateUserToken(String token) {
		final var secret = jwtProperties.getActivateUserJwt().getSecret();
		return validateToken(token, secret);
	}

	public Mono<String> validateActivateEmployeeToken(String token) {
		final var secret = jwtProperties.getActivateEmployeeJwt().getSecret();
		return validateToken(token, secret);
	}

	public Mono<String> validateActivateCompanyAdminToken(String token) {
		final var secret = jwtProperties.getActivateCompanyAdminJwt().getSecret();
		return validateToken(token, secret);
	}

	public Mono<String> validateCompanyAdminRegisterToken(String token) {
		final var secret = jwtProperties.getRegisterCompanyAdminJwt().getSecret();
		return validateToken(token, secret);
	}

	private Mono<String> validateToken(String token, String secret) {
		return Mono.just(JWT.require(Algorithm.HMAC256(secret)).build())
				.map(jwtVerifier -> jwtVerifier.verify(token))
				.map(DecodedJWT::getSubject);
	}
}
