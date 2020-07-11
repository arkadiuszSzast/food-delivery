package com.food.delivery.accountservice.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.JwtException;
import reactivefeign.FallbackFactory;
import reactor.core.publisher.Mono;

@Slf4j
class JwtClientFallbackFactory implements FallbackFactory<JwtServiceClient> {
	@Override
	public JwtServiceClient apply(Throwable throwable) {
		return new JwtServiceClient() {
			@Override
			public Mono<String> validateUserActivateToken(String token) {
				return Mono.error(new JwtException("Provided user activation token is invalid"));
			}

			@Override
			public Mono<String> validateEmployeeActivateToken(String token) {
				return Mono.error(new JwtException("Provided employee activation token is invalid"));
			}

			@Override
			public Mono<String> validateCompanyAdminActivateToken(String token) {
				return Mono.error(new JwtException("Provided company admin activation token is invalid"));
			}

			@Override
			public Mono<String> validateCompanyAdminRegisterToken(String token) {
				return Mono.error(new JwtException("Provided company admin register token is invalid"));
			}
		};
	}
}
