package com.food.delivery.companyservice.company.domain;

import com.food.delivery.companyservice.company.address.Address;
import com.food.delivery.companyservice.utils.validators.PhoneNumberValidator;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Setter
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Company {

	@Id
	private String id;
	@NotNull
	private String companyAdminId;
	@NotNull
	private String name;
	@PhoneNumberValidator
	private String phoneNumber;
	private Address address;

}
