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
	@DisplayName("Should generate user activate token")
	void shouldGenerateUserActivateToken() {
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

	@Test
	@DisplayName("Should generate employee activate token")
	void shouldGenerateEmployeeActivateToken() {
		//arrange
		final var userId = "oktaUserId";
		final var activateEmployeeProperties = jwtProperties.getActivateEmployeeJwt();
		final var issuer = activateEmployeeProperties.getIssuer();

		//act
		final var result = jwtGenerateService.getEmployeeActivateJwt(userId);

		//assert
		assertAll(
				() -> assertThat(result).isNotNull(),
				() -> assertThat(JWT.decode(result).getSubject()).isEqualTo(userId),
				() -> assertThat(JWT.decode(result).getIssuer()).isEqualTo(issuer)
		);
	}

	@Test
	@DisplayName("Should generate company admin activate token")
	void shouldGenerateCompanyAdminActivateToken() {
		//arrange
		final var userId = "oktaUserId";
		final var activateCompanyAdminProperties = jwtProperties.getActivateCompanyAdminJwt();
		final var issuer = activateCompanyAdminProperties.getIssuer();

		//act
		final var result = jwtGenerateService.getCompanyAdminActivateJwt(userId);

		//assert
		assertAll(
				() -> assertThat(result).isNotNull(),
				() -> assertThat(JWT.decode(result).getSubject()).isEqualTo(userId),
				() -> assertThat(JWT.decode(result).getIssuer()).isEqualTo(issuer)
		);
	}

	@Test
	@DisplayName("Should generate company admin register token")
	void shouldGenerateCompanyAdminRegisterToken() {
		//arrange
		final var userId = "oktaUserId";
		final var registerCompanyAdminProperties = jwtProperties.getRegisterCompanyAdminJwt();
		final var issuer = registerCompanyAdminProperties.getIssuer();

		//act
		final var result = jwtGenerateService.getCompanyAdminRegisterJwt(userId);

		//assert
		assertAll(
				() -> assertThat(result).isNotNull(),
				() -> assertThat(JWT.decode(result).getSubject()).isEqualTo(userId),
				() -> assertThat(JWT.decode(result).getIssuer()).isEqualTo(issuer)
		);
	}

}
