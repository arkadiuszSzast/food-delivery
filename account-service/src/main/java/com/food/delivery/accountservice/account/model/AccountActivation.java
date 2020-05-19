package com.food.delivery.accountservice.account.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountActivation {

	private final String activationToken;
	private final String activationUrl;
}
