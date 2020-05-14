package com.food.delivery.jwtservice.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.food.delivery.jwtservice.support.JwtServiceIntegrationTest;
import com.food.delivery.jwtservice.utils.properties.jwt.JwtProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JwtServiceIntegrationTest
class JwtValidationServiceTestIT {

	@Autowired
	private JwtProperties jwtProperties;
	@Autowired
	private JwtValidationService jwtValidationService;

	@Test
	@DisplayName("Should successfully validate token")
	void shouldSuccessfullyValidateToken() {
		//arrange
		final var subject = "oktaUserId";
		final var activateAccountProperties = jwtProperties.getActivateAccount();
		final var secret = activateAccountProperties.getSecret();
		final var expirationTime = activateAccountProperties.getExpirationTime();
		final var token = JWT.create()
				.withSubject(subject)
				.withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
				.sign(Algorithm.HMAC256(secret));

		//act
		final var result = jwtValidationService.validateActivateAccountToken(token).block();

		//assert
		assertThat(result).isEqualTo(subject);

	}

	@Test
	@DisplayName("Should throw exception when token has invalid signature")
	void shouldThrowExceptionWhenTokenHasInvalidSignature() {
		//arrange
		final var badSecret = "BAD_SECRET";
		final var activateAccountProperties = jwtProperties.getActivateAccount();
		final var expirationTime = activateAccountProperties.getExpirationTime();
		final var token = JWT.create()
				.withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
				.sign(Algorithm.HMAC256(badSecret));

		//act && assert
		assertThrows(SignatureVerificationException.class,
				() -> jwtValidationService.validateActivateAccountToken(token).block());
	}

	@Test
	@DisplayName("Should throw exception when token expired")
	void shouldThrowExceptionWhenTokenExpired() throws InterruptedException {
		//arrange
		final var activateAccountProperties = jwtProperties.getActivateAccount();
		final var secret = activateAccountProperties.getSecret();
		final var token = JWT.create()
				.withExpiresAt(new Date(System.currentTimeMillis() - 1000))
				.sign(Algorithm.HMAC256(secret));

		//act && assert
		assertThrows(TokenExpiredException.class,
				() -> jwtValidationService.validateActivateAccountToken(token).block());
	}
}
