package com.food.delivery.accountservice.user;

import com.food.delivery.accountservice.account.AccountActivation;
import com.food.delivery.accountservice.jwt.JwtServiceClient;
import com.food.delivery.accountservice.user.okta.OktaAdapterAccountClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserActivateService {

	private final JwtServiceClient jwtServiceClient;
	private final OktaAdapterAccountClient oktaAdapterAccountClient;

	public Mono<AccountActivation> activateAccount(String token) {
		return jwtServiceClient.validateUserActivateToken(token)
				.flatMap(oktaAdapterAccountClient::activateAccount);
	}
}
