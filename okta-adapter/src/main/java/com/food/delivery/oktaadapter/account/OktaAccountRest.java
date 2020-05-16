package com.food.delivery.oktaadapter.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OktaAccountRest {

	private final AccountRest accountRest;
	private final String oktaId;

}
