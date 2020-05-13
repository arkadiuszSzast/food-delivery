package com.food.delivery.oktaadapter.utils;

import com.food.delivery.oktaadapter.utils.properties.OktaProperties;
import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OktaClient {

	private final OktaProperties oktaProperties;

	@Bean
	Client client() {
		return Clients.builder()
				.setOrgUrl(oktaProperties.getDomainUrl())
				.setClientCredentials(new TokenClientCredentials(oktaProperties.getApiToken()))
				.build();
	}
}
