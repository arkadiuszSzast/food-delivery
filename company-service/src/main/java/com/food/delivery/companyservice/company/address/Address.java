package com.food.delivery.companyservice.company.address;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.constraint.CharSequenceConstraint;
import am.ik.yavi.core.ConstraintViolations;
import am.ik.yavi.core.Validator;
import am.ik.yavi.fn.Either;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Transient;


@Getter
@Builder
@AllArgsConstructor
public class Address {

	@Transient
	private final Validator<Address> validator = ValidatorBuilder.<Address>of()
			.constraint(Address::getCity, "city", CharSequenceConstraint::notBlank)
			.constraint(Address::getZipCode, "zipCode", CharSequenceConstraint::notBlank)
			.build();

	private final String city;
	private final String zipCode;
	private final String street;
	private final String houseNumber;

	public Either<ConstraintViolations, Address> validate() {
		return validator.validateToEither(this);
	}
}
