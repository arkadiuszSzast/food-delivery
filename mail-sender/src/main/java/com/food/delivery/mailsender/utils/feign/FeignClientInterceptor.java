package com.food.delivery.mailsender.utils.feign;

import com.food.delivery.mailsender.utils.properties.SendgridProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactivefeign.client.ReactiveHttpRequest;
import reactivefeign.client.ReactiveHttpRequestInterceptor;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@AllArgsConstructor
public class FeignClientInterceptor implements ReactiveHttpRequestInterceptor {

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String BEARER_TOKEN_TYPE = "Bearer";

	private final SendgridProperties sendgridProperties;

	@Override
	public Mono<ReactiveHttpRequest> apply(ReactiveHttpRequest reactiveHttpRequest) {
		return Mono.just(sendgridProperties.getApiKey())
				.flatMap(apiKey -> {
					reactiveHttpRequest.headers()
							.putIfAbsent(AUTHORIZATION_HEADER,
									List.of(String.format("%s %s", BEARER_TOKEN_TYPE, apiKey)));
					return Mono.just(reactiveHttpRequest);
				})
				.switchIfEmpty(Mono.just(reactiveHttpRequest));
	}
}
