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
	@DisplayName("Should successfully validate user activate token")
	void shouldSuccessfullyValidateUserActivateToken() {
		//arrange
		final var subject = "oktaUserId";
		final var activateAccountProperties = jwtProperties.getActivateUserJwt();
		final var secret = activateAccountProperties.getSecret();
		final var expirationTime = activateAccountProperties.getExpirationTime();
		final var token = JWT.create()
				.withSubject(subject)
				.withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
				.sign(Algorithm.HMAC256(secret));

		//act
		final var result = jwtValidationService.validateActivateUserToken(token).block();

		//assert
		assertThat(result).isEqualTo(subject);

	}

	@Test
	@DisplayName("Should throw exception when user activate token has invalid signature")
	void shouldThrowExceptionWhenUserActivateTokenHasInvalidSignature() {
		//arrange
		final var badSecret = "BAD_SECRET";
		final var activateAccountProperties = jwtProperties.getActivateUserJwt();
		final var expirationTime = activateAccountProperties.getExpirationTime();
		final var token = JWT.create()
				.withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
				.sign(Algorithm.HMAC256(badSecret));

		//act && assert
		assertThrows(SignatureVerificationException.class,
				() -> jwtValidationService.validateActivateUserToken(token).block());
	}

	@Test
	@DisplayName("Should throw exception when user activate token expired")
	void shouldThrowExceptionWhenUserActivateTokenExpired() {
		//arrange
		final var activateAccountProperties = jwtProperties.getActivateUserJwt();
		final var secret = activateAccountProperties.getSecret();
		final var token = JWT.create()
				.withExpiresAt(new Date(System.currentTimeMillis() - 1000))
				.sign(Algorithm.HMAC256(secret));

		//act && assert
		assertThrows(TokenExpiredException.class,
				() -> jwtValidationService.validateActivateUserToken(token).block());
	}

	@Test
	@DisplayName("Should successfully validate employee activate token")
	void shouldSuccessfullyValidateEmployeeActivateToken() {
		//arrange
		final var subject = "oktaUserId";
		final var activateAccountProperties = jwtProperties.getActivateEmployeeJwt();
		final var secret = activateAccountProperties.getSecret();
		final var expirationTime = activateAccountProperties.getExpirationTime();
		final var token = JWT.create()
				.withSubject(subject)
				.withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
				.sign(Algorithm.HMAC256(secret));

		//act
		final var result = jwtValidationService.validateActivateEmployeeToken(token).block();

		//assert
		assertThat(result).isEqualTo(subject);

	}

	@Test
	@DisplayName("Should throw exception when employee activate token has invalid signature")
	void shouldThrowExceptionWhenEmployeeActivateTokenHasInvalidSignature() {
		//arrange
		final var badSecret = "BAD_SECRET";
		final var activateAccountProperties = jwtProperties.getActivateEmployeeJwt();
		final var expirationTime = activateAccountProperties.getExpirationTime();
		final var token = JWT.create()
				.withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
				.sign(Algorithm.HMAC256(badSecret));

		//act && assert
		assertThrows(SignatureVerificationException.class,
				() -> jwtValidationService.validateActivateEmployeeToken(token).block());
	}

	@Test
	@DisplayName("Should throw exception when employee activate token expired")
	void shouldThrowExceptionWhenEmployeeActivateTokenExpired() {
		//arrange
		final var activateAccountProperties = jwtProperties.getActivateUserJwt();
		final var secret = activateAccountProperties.getSecret();
		final var token = JWT.create()
				.withExpiresAt(new Date(System.currentTimeMillis() - 1000))
				.sign(Algorithm.HMAC256(secret));

		//act && assert
		assertThrows(TokenExpiredException.class,
				() -> jwtValidationService.validateActivateUserToken(token).block());
	}

	@Test
	@DisplayName("Should successfully validate company admin activate token")
	void shouldSuccessfullyValidateCompanyAdminActivateToken() {
		//arrange
		final var subject = "oktaUserId";
		final var activateAccountProperties = jwtProperties.getActivateCompanyAdminJwt();
		final var secret = activateAccountProperties.getSecret();
		final var expirationTime = activateAccountProperties.getExpirationTime();
		final var token = JWT.create()
				.withSubject(subject)
				.withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
				.sign(Algorithm.HMAC256(secret));

		//act
		final var result = jwtValidationService.validateActivateCompanyAdminToken(token).block();

		//assert
		assertThat(result).isEqualTo(subject);

	}

	@Test
	@DisplayName("Should throw exception when company admin activate token has invalid signature")
	void shouldThrowExceptionWhenCompanyAdminActivateTokenHasInvalidSignature() {
		//arrange
		final var badSecret = "BAD_SECRET";
		final var activateAccountProperties = jwtProperties.getActivateCompanyAdminJwt();
		final var expirationTime = activateAccountProperties.getExpirationTime();
		final var token = JWT.create()
				.withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
				.sign(Algorithm.HMAC256(badSecret));

		//act && assert
		assertThrows(SignatureVerificationException.class,
				() -> jwtValidationService.validateActivateCompanyAdminToken(token).block());
	}

	@Test
	@DisplayName("Should throw exception when company admin activate token expired")
	void shouldThrowExceptionWhenCompanyAdminActivateTokenExpired() {
		//arrange
		final var activateAccountProperties = jwtProperties.getActivateCompanyAdminJwt();
		final var secret = activateAccountProperties.getSecret();
		final var token = JWT.create()
				.withExpiresAt(new Date(System.currentTimeMillis() - 1000))
				.sign(Algorithm.HMAC256(secret));

		//act && assert
		assertThrows(TokenExpiredException.class,
				() -> jwtValidationService.validateActivateCompanyAdminToken(token).block());
	}

	@Test
	@DisplayName("Should successfully validate company admin register token")
	void shouldSuccessfullyValidateCompanyAdminRegisterToken() {
		//arrange
		final var subject = "oktaUserId";
		final var activateAccountProperties = jwtProperties.getRegisterCompanyAdminJwt();
		final var secret = activateAccountProperties.getSecret();
		final var expirationTime = activateAccountProperties.getExpirationTime();
		final var token = JWT.create()
				.withSubject(subject)
				.withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
				.sign(Algorithm.HMAC256(secret));

		//act
		final var result = jwtValidationService.validateCompanyAdminRegisterToken(token).block();

		//assert
		assertThat(result).isEqualTo(subject);

	}

	@Test
	@DisplayName("Should throw exception when company admin register token has invalid signature")
	void shouldThrowExceptionWhenCompanyAdminRegisterTokenHasInvalidSignature() {
		//arrange
		final var badSecret = "BAD_SECRET";
		final var activateAccountProperties = jwtProperties.getRegisterCompanyAdminJwt();
		final var expirationTime = activateAccountProperties.getExpirationTime();
		final var token = JWT.create()
				.withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
				.sign(Algorithm.HMAC256(badSecret));

		//act && assert
		assertThrows(SignatureVerificationException.class,
				() -> jwtValidationService.validateCompanyAdminRegisterToken(token).block());
	}

	@Test
	@DisplayName("Should throw exception when company admin register token expired")
	void shouldThrowExceptionWhenCompanyAdminRegisterTokenExpired() {
		//arrange
		final var activateAccountProperties = jwtProperties.getRegisterCompanyAdminJwt();
		final var secret = activateAccountProperties.getSecret();
		final var token = JWT.create()
				.withExpiresAt(new Date(System.currentTimeMillis() - 1000))
				.sign(Algorithm.HMAC256(secret));

		//act && assert
		assertThrows(TokenExpiredException.class,
				() -> jwtValidationService.validateCompanyAdminRegisterToken(token).block());
	}
}
