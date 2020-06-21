package com.food.delivery.accountservice.utils;

import org.springframework.security.oauth2.jwt.Jwt;

public class JwtProvider {

	public static Jwt getEmptyJwt() {
		return Jwt.withTokenValue("empty")
				.header("", "")
				.claim("", "")
				.build();
	}

	public static Jwt getJwtWithSubject(String subject) {
		return Jwt.withTokenValue("empty")
				.subject(subject)
				.header("", "")
				.claim("", "")
				.build();
	}
}
