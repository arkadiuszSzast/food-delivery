package com.food.delivery.companyservice.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AccountRest {

	private final String id;
	private final String firstName;
	private final String lastName;
	private final String email;

}
