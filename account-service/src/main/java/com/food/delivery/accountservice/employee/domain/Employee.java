package com.food.delivery.accountservice.employee.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	@Id
	private String id;
	private String firstName;
	private String lastName;
	@Indexed(unique = true)
	private String email;
	private String oktaId;
	@Setter
	private String companyId;

}
