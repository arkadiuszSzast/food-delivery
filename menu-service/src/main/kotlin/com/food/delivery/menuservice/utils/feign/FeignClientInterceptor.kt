package com.food.delivery.menuservice.utils.feign

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component
import reactivefeign.client.ReactiveHttpRequest
import reactivefeign.client.ReactiveHttpRequestInterceptor
import reactor.core.publisher.Mono

@Component
class FeignClientInterceptor : ReactiveHttpRequestInterceptor {
	override fun apply(reactiveHttpRequest: ReactiveHttpRequest): Mono<ReactiveHttpRequest> {
		return ReactiveSecurityContextHolder.getContext()
				.map { obj: SecurityContext -> obj.authentication }
				.map { obj: Authentication -> obj.principal }
				.cast(Jwt::class.java)
				.flatMap { jwt: Jwt ->
					reactiveHttpRequest.headers()
							.putIfAbsent(AUTHORIZATION_HEADER,
									listOf(String.format("%s %s", BEARER_TOKEN_TYPE, jwt.tokenValue)))
					Mono.just(reactiveHttpRequest)
				}
				.switchIfEmpty(Mono.just(reactiveHttpRequest))
	}

	companion object {
		private const val AUTHORIZATION_HEADER = "Authorization"
		private const val BEARER_TOKEN_TYPE = "Bearer"
	}
}
