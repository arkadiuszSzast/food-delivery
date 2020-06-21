package com.food.delivery.mailsender.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompanyAdminRegisterEvent {

	private final String email;
}
