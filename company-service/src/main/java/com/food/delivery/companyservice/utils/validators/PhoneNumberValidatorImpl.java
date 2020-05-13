package com.food.delivery.companyservice.utils.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PhoneNumberValidatorImpl implements ConstraintValidator<PhoneNumberValidator, String> {

	private static final String PHONE_NUMBER_REGEX = "^[+]*[(]?[0-9]{1,4}[)]?[-\\s./0-9]{0,25}[0-9]$";

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value.matches(PHONE_NUMBER_REGEX);
	}
}
