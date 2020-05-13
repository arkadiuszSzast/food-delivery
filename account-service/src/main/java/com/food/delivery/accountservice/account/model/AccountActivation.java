package com.food.delivery.accountservice.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AccountActivation {

	private final String activationToken;
	private final String activationUrl;
}
