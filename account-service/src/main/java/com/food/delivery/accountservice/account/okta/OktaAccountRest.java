package com.food.delivery.accountservice.account.okta;

import com.food.delivery.accountservice.account.model.AccountRest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OktaAccountRest {

	private final AccountRest accountRest;
	private final String oktaId;
}
