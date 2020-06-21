package com.food.delivery.jwtservice.utils.properties.jwt;

import com.food.delivery.jwtservice.support.JwtServiceIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JwtServiceIntegrationTest
class JwtPropertiesTestIT {

	private final static String ACTIVATE_ACCOUNT_JWT_ISSUER = "food-delivery";
	private final static Long ACTIVATE_ACCOUNT_JWT_EXPIRATION_TIME = 86400000L;
	private final static String ACTIVATE_ACCOUNT_JWT_SECRET = "secret_password";

	@Autowired
	private JwtProperties jwtProperties;

	@Test
	@DisplayName("Should get activate account jwt properties")
	void shouldGetActivateAccountJwtProperties() {
		//arrange && act
		final var result = jwtProperties.getActivateUserJwt();

		//assert
		assertAll(
				() -> assertThat(result.getIssuer()).isEqualTo(ACTIVATE_ACCOUNT_JWT_ISSUER),
				() -> assertThat(result.getExpirationTime()).isEqualTo(ACTIVATE_ACCOUNT_JWT_EXPIRATION_TIME),
				() -> assertThat(result.getSecret()).isEqualTo(ACTIVATE_ACCOUNT_JWT_SECRET)
		);
	}

}
