package com.food.delivery.companyservice.utils.validators;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Patterns {

	private static final String PHONE_NUMBER_REGEX = "^[+]*[(]?[0-9]{1,4}[)]?[-\\s./0-9]{0,25}[0-9]$";

	public static String getPhoneNumberRegex() {
		return PHONE_NUMBER_REGEX;
	}
}
