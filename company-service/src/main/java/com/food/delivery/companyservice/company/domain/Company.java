package com.food.delivery.companyservice.company.domain;

import com.food.delivery.companyservice.company.address.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Company {

	@Id
	private String id;
	@Indexed(unique = true)
	private String companyAdminId;
	@Indexed(unique = true)
	private String name;
	private String phoneNumber;
	private Address address;

}
