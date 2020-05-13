package com.food.delivery.accountservice.account;

import com.food.delivery.accountservice.account.model.AccountActivation;
import com.food.delivery.accountservice.account.okta.OktaAdapterAccountClient;
import com.food.delivery.accountservice.jwt.JwtServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AccountActivateService {

	private final JwtServiceClient jwtServiceClient;
	private final OktaAdapterAccountClient oktaAdapterAccountClient;

	public Mono<AccountActivation> activateAccount(String token) {
		return jwtServiceClient.validateAccountActivateToken(token)
				.flatMap(oktaAdapterAccountClient::activateAccount);
	}
}
