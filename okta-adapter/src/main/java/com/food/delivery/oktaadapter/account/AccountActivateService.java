package com.food.delivery.oktaadapter.account;

import com.okta.sdk.client.Client;
import com.okta.sdk.resource.user.UserActivationToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AccountActivateService {

	private final Client oktaClient;

	public Mono<UserActivationToken> activateAccount(String userId) {
		return Mono.just(oktaClient.getUser(userId))
				.map(user -> user.activate(false));
	}
}
