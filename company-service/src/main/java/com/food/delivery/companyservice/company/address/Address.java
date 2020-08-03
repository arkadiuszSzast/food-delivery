package com.food.delivery.companyservice.company.address;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.constraint.CharSequenceConstraint;
import am.ik.yavi.core.Validator;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public final class Address {

	private final Validator<Address> validator = ValidatorBuilder.<Address>of()
			.constraint(Address::getCity, "city", CharSequenceConstraint::notBlank)
			.constraint(Address::getZipCode, "zipCode", CharSequenceConstraint::notBlank)
			.build();

	private final String city;
	private final String zipCode;
	private final String street;
	private final String houseNumber;
}
