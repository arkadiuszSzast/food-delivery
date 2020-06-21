package com.food.delivery.accountservice.employee.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompanyAdminRegisterEvent {

	private final String email;
}
