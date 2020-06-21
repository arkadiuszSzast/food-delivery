package com.food.delivery.mailsender.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserActivateEvent {

	private final String firstName;
	private final String email;
	private final String oktaId;

}
