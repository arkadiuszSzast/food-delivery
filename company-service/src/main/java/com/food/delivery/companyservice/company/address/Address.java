package com.food.delivery.companyservice.company.address;

import lombok.AllArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
public final class Address {

	@NotNull
	private final String city;
	@NotNull
	private final String zipCode;
	private final String street;
	private final String houseNumber;
}
