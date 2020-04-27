package com.food.delivery.mailsender.account;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccountActivateEvent {

	private final String firstName;
	private final String email;

}
