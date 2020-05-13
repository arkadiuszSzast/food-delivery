package com.food.delivery.jwtservice.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.food.delivery.jwtservice.utils.properties.jwt.ActivateAccountJwt;
import com.food.delivery.jwtservice.utils.properties.jwt.JwtProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtValidationServiceTest {

	@Mock
	private JwtProperties jwtProperties;
	@InjectMocks
	private JwtValidationService jwtValidationService;

	@Test
	@DisplayName("Should successfully validate token")
	void shouldSuccessfullyValidateToken() {
		//arrange
		final var secret = "Bm30lw0I";
		final var expirationTime = 86400000L;
		when(jwtProperties.getActivateAccount()).thenReturn(new ActivateAccountJwt(secret, "issuer", expirationTime));
		final var token = JWT.create()
				.withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
				.sign(Algorithm.HMAC256(secret));

		//act && assert
		jwtValidationService.validateActivateAccountToken(token);
	}

}
