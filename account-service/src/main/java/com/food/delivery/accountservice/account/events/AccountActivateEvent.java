package com.food.delivery.accountservice.account.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountActivateEvent {

	private final String email;
	private final String firstName;

}
