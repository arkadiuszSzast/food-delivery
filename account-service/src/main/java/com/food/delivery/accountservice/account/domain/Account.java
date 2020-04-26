package com.food.delivery.accountservice.account.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	@Id
	private String id;
	private String firstName;
	private String lastName;
	private String email;

}
