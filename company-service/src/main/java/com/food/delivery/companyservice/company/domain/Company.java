package com.food.delivery.companyservice.company.domain;

import com.food.delivery.companyservice.company.address.Address;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Builder
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
