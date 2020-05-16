package com.food.delivery.oktaadapter.support.account;

import com.food.delivery.oktaadapter.account.AccountRest;

public class AccountRestFactory {

	public static AccountRest getAccountRest() {
		final var firstName = "Joe";
		final var lastName = "Doe";
		final var email = "joe@mail.com";
		return new AccountRest(firstName, lastName, email);
	}
}
