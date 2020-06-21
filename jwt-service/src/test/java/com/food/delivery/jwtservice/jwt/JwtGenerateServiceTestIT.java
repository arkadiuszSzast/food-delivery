package com.food.delivery.jwtservice.jwt;

import com.auth0.jwt.JWT;
import com.food.delivery.jwtservice.support.JwtServiceIntegrationTest;
import com.food.delivery.jwtservice.utils.properties.jwt.JwtProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JwtServiceIntegrationTest
class JwtGenerateServiceTestIT {

	@Autowired
	private JwtGenerateService jwtGenerateService;
	@Autowired
	private JwtProperties jwtProperties;

	@Test
	@DisplayName("Should generate token")
	void shouldGenerateToken() {
		//arrange
		final var userId = "oktaUserId";
		final var activateAccountProperties = jwtProperties.getActivateUserJwt();
		final var issuer = activateAccountProperties.getIssuer();

		//act
		final var result = jwtGenerateService.getUserActivateJwt(userId);

		//assert
		assertAll(
				() -> assertThat(result).isNotNull(),
				() -> assertThat(JWT.decode(result).getSubject()).isEqualTo(userId),
				() -> assertThat(JWT.decode(result).getIssuer()).isEqualTo(issuer)
		);
	}

}
