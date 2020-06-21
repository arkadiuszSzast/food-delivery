package com.food.delivery.jwtservice.utils.properties.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BasicJwt implements JwtToken {

	private final String secret;
	private final String issuer;
	private final Long expirationTime;

}
