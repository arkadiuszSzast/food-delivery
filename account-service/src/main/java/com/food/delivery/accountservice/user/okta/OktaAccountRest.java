package com.food.delivery.accountservice.user.okta;

import com.food.delivery.accountservice.account.AccountRest;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OktaAccountRest {

	private final AccountRest accountRest;
	private final String oktaId;
}
