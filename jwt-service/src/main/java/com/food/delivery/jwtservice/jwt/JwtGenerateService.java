package com.food.delivery.jwtservice.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.food.delivery.jwtservice.utils.properties.jwt.JwtProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JwtGenerateService {

	private final JwtProperties jwtProperties;

	public String getAccountActivateJwt(String oktaUserId) {
		final var activateAccountJwt = jwtProperties.getActivateAccount();
		return JWT.create()
				.withSubject(oktaUserId)
				.withIssuer(activateAccountJwt.getIssuer())
				.withExpiresAt(new Date(System.currentTimeMillis() + activateAccountJwt.getExpirationTime()))
				.sign(Algorithm.HMAC256(activateAccountJwt.getSecret()));
	}
}
