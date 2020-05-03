package com.food.delivery.accountservice.utils.feign;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import reactivefeign.client.ReactiveHttpRequest;
import reactivefeign.client.ReactiveHttpRequestInterceptor;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class FeignClientInterceptor implements ReactiveHttpRequestInterceptor {

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String BEARER_TOKEN_TYPE = "Bearer";

	@Override
	public Mono<ReactiveHttpRequest> apply(ReactiveHttpRequest reactiveHttpRequest) {
		return ReactiveSecurityContextHolder.getContext()
				.map(SecurityContext::getAuthentication)
				.map(Authentication::getPrincipal)
				.cast(Jwt.class)
				.flatMap(jwt -> {
					reactiveHttpRequest.headers()
							.putIfAbsent(AUTHORIZATION_HEADER,
									List.of(String.format("%s %s", BEARER_TOKEN_TYPE, jwt.getTokenValue())));
					return Mono.just(reactiveHttpRequest);
				})
				.switchIfEmpty(Mono.just(reactiveHttpRequest));
	}
}
