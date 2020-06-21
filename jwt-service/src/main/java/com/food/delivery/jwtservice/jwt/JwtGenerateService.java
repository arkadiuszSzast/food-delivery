package com.food.delivery.jwtservice.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.food.delivery.jwtservice.utils.properties.jwt.BasicJwt;
import com.food.delivery.jwtservice.utils.properties.jwt.JwtProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JwtGenerateService {

	private final JwtProperties jwtProperties;

	public String getUserActivateJwt(String oktaUserId) {
		final var activateUserJwt = jwtProperties.getActivateUserJwt();
		return getAccountActivateJwt(activateUserJwt, oktaUserId);
	}

	public String getEmployeeActivateJwt(String oktaUserId) {
		final var activateEmployeeJwt = jwtProperties.getActivateEmployeeJwt();
		return getAccountActivateJwt(activateEmployeeJwt, oktaUserId);
	}

	public String getCompanyAdminActivateJwt(String oktaUserId) {
		final var activateCompanyAdminJwt = jwtProperties.getActivateCompanyAdminJwt();
		return getAccountActivateJwt(activateCompanyAdminJwt, oktaUserId);
	}

	public String getCompanyAdminRegisterJwt(String email) {
		final var registerCompanyAdminJwt = jwtProperties.getRegisterCompanyAdminJwt();
		return getAccountActivateJwt(registerCompanyAdminJwt, email);
	}

	private String getAccountActivateJwt(BasicJwt basicJwt, String subject) {
		return JWT.create()
				.withSubject(subject)
				.withIssuer(basicJwt.getIssuer())
				.withExpiresAt(new Date(System.currentTimeMillis() + basicJwt.getExpirationTime()))
				.sign(Algorithm.HMAC256(basicJwt.getSecret()));
	}
}
