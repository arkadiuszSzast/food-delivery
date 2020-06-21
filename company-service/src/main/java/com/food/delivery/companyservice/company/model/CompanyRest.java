package com.food.delivery.companyservice.company.model;

import com.food.delivery.companyservice.utils.validators.PhoneNumberValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompanyRest {

	private final String id;
	private final String name;
	@PhoneNumberValidator
	private final String phoneNumber;

}
