package com.food.delivery.companyservice.company.model;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.ConstraintViolations;
import am.ik.yavi.core.Validator;
import am.ik.yavi.fn.Either;
import com.food.delivery.companyservice.utils.validators.Patterns;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CompanyRest {

	private final Validator<CompanyRest> validator = ValidatorBuilder.<CompanyRest>of()
			.constraint(CompanyRest::getPhoneNumber, "phone",
					phone -> phone.pattern(Patterns.getPhoneNumberRegex()))
			.build();

	private final String id;
	private final String name;
	private final String phoneNumber;

	public Either<ConstraintViolations, CompanyRest> validate() {
		return validator.validateToEither(this);
	}

}
