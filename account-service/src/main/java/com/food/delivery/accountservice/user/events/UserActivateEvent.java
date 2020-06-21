package com.food.delivery.accountservice.user.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserActivateEvent {

	private final String id;
	private final String email;
	private final String firstName;
	private final String oktaId;

}
