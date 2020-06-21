package com.food.delivery.jwtservice.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.food.delivery.jwtservice.utils.properties.jwt.BasicJwt;
import com.food.delivery.jwtservice.utils.properties.jwt.JwtProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtValidationServiceTest {

	@Mock
	private JwtProperties jwtProperties;
	@InjectMocks
	private JwtValidationService jwtValidationService;

	@Test
	@DisplayName("Should successfully validate user activate token")
	void shouldSuccessfullyValidateUserActivateToken() {
		//arrange
		final var issuer = "issuer";
		final var secret = "Bm30lw0I";
		final var subject = "oktaUserId";
		final var expirationTime = 86400000L;
		final var token = JWT.create()
				.withSubject(subject)
				.withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
				.sign(Algorithm.HMAC256(secret));
		when(jwtProperties.getActivateUserJwt()).thenReturn(new BasicJwt(secret, issuer, expirationTime));

		//act
		final var result = jwtValidationService.validateActivateUserToken(token).block();

		//assert
		assertThat(result).isEqualTo(subject);
	}

	@Test
	@DisplayName("Should successfully validate employee activate token")
	void shouldSuccessfullyValidateEmployeeActivateToken() {
		//arrange
		final var issuer = "issuer";
		final var secret = "Bm30lw0I";
		final var subject = "oktaUserId";
		final var expirationTime = 86400000L;
		final var token = JWT.create()
				.withSubject(subject)
				.withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
				.sign(Algorithm.HMAC256(secret));
		when(jwtProperties.getActivateEmployeeJwt()).thenReturn(new BasicJwt(secret, issuer, expirationTime));

		//act
		final var result = jwtValidationService.validateActivateEmployeeToken(token).block();

		//assert
		assertThat(result).isEqualTo(subject);
	}

	@Test
	@DisplayName("Should successfully validate company admin activate token")
	void shouldSuccessfullyValidateCompanyAdminActivateToken() {
		//arrange
		final var issuer = "issuer";
		final var secret = "Bm30lw0I";
		final var subject = "oktaUserId";
		final var expirationTime = 86400000L;
		final var token = JWT.create()
				.withSubject(subject)
				.withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
				.sign(Algorithm.HMAC256(secret));
		when(jwtProperties.getActivateCompanyAdminJwt()).thenReturn(new BasicJwt(secret, issuer, expirationTime));

		//act
		final var result = jwtValidationService.validateActivateCompanyAdminToken(token).block();

		//assert
		assertThat(result).isEqualTo(subject);
	}

	@Test
	@DisplayName("Should successfully validate company admin register token")
	void shouldSuccessfullyValidateCompanyAdminRegisterToken() {
		//arrange
		final var issuer = "issuer";
		final var secret = "Bm30lw0I";
		final var subject = "oktaUserId";
		final var expirationTime = 86400000L;
		final var token = JWT.create()
				.withSubject(subject)
				.withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
				.sign(Algorithm.HMAC256(secret));
		when(jwtProperties.getRegisterCompanyAdminJwt()).thenReturn(new BasicJwt(secret, issuer, expirationTime));

		//act
		final var result = jwtValidationService.validateCompanyAdminRegisterToken(token).block();

		//assert
		assertThat(result).isEqualTo(subject);
	}

}
