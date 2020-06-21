package com.food.delivery.mailsender.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmployeeActivateEvent {

	private final UserActivateEvent userActivateEvent;
	private final String companyName;
}
