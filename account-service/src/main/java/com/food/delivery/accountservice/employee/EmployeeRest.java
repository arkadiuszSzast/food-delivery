package com.food.delivery.accountservice.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.food.delivery.accountservice.account.AccountRest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EmployeeRest {

	@JsonProperty("account")
	private final AccountRest accountRest;
	private final String companyName;
}
