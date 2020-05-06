package com.food.delivery.oktaadapter.account;

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
