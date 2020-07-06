package com.food.delivery.mailsender.jwt;

import reactivefeign.FallbackFactory;
import reactor.core.publisher.Mono;

class JwtClientFallbackFactory implements FallbackFactory<JwtServiceClient> {
	@Override
	public JwtServiceClient apply(Throwable throwable) {
		return new JwtServiceClient() {
			@Override
			public Mono<String> getUserActivateJwt(String oktaUserId) {
				return Mono.empty();
			}

			@Override
			public Mono<String> getEmployeeActivateJwt(String oktaUserId) {
				return null;
			}

			@Override
			public Mono<String> getCompanyAdminActivateJwt(String oktaUserId) {
				return null;
			}

			@Override
			public Mono<String> getCompanyAdminRegisterJwt(String email) {
				return null;
			}
		};
	}
}
