package com.food.delivery.jwtservice.utils.properties.jwt;

public interface JwtToken {
	String getSecret();

	String getIssuer();

	Long getExpirationTime();
}
