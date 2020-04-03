package com.food.delivery.companyservice.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Account {

	@Getter
	private final String id;
	private final String name;
	private final String surname;
	private final String email;
}
