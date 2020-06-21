package com.food.delivery.accountservice.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountActivation {

	private final String activationToken;
	private final String activationUrl;
}
