package com.food.delivery.gateway.token;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class BlacklistedToken {

	@Id
	private String id;
	@Indexed(unique = true)
	private final String token;
	@CreatedDate
	private LocalDateTime createdDate;

	public BlacklistedToken(String token) {
		this.token = token;
	}

	public String getId() {
		return id;
	}

	public String getToken() {
		return token;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

}
