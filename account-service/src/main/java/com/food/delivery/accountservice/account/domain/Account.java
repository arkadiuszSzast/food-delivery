package com.food.delivery.accountservice.account.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

	@Id
	private String id;
	private String name;
	private String surname;
	private String email;

	public Account(String email) {
		this.email = email;
	}
}
