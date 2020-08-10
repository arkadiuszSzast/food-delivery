package com.food.delivery.companyservice.account;

import com.fasterxml.jackson.annotation.JsonProperty;
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
	private final String companyId;
}
