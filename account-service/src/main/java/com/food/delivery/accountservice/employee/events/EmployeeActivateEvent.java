package com.food.delivery.accountservice.employee.events;

import com.food.delivery.accountservice.user.events.UserActivateEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmployeeActivateEvent {

	private final UserActivateEvent userActivateEvent;
	private final String companyName;
}
