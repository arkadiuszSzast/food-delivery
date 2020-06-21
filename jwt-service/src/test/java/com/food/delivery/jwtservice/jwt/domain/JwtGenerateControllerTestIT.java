package com.food.delivery.jwtservice.jwt.domain;

import com.auth0.jwt.JWT;
import com.food.delivery.jwtservice.support.JwtServiceIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JwtServiceIntegrationTest
class JwtGenerateControllerTestIT {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	@DisplayName("Should return user activation token")
	void shouldReturnUserActivationToken() {

		//arrange
		final var userId = "exampleUser";

		//act
		final var result = webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/jwt/user-activate")
						.queryParam("oktaUserId", userId)
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class)
				.returnResult()
				.getResponseBody();

		//assert
		assertAll(
				() -> assertThat(result).isNotNull(),
				() -> assertThat(JWT.decode(result).getSubject()).isEqualTo(userId)
		);
	}

	@Test
	@DisplayName("Should return employee activation token")
	void shouldReturnEmployeeActivationToken() {

		//arrange
		final var userId = "exampleUser";

		//act
		final var result = webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/jwt/employee-activate")
						.queryParam("oktaUserId", userId)
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class)
				.returnResult()
				.getResponseBody();

		//assert
		assertAll(
				() -> assertThat(result).isNotNull(),
				() -> assertThat(JWT.decode(result).getSubject()).isEqualTo(userId)
		);
	}

	@Test
	@DisplayName("Should return company admin activation token")
	void shouldReturnCompanyAdminActivationToken() {

		//arrange
		final var userId = "exampleUser";

		//act
		final var result = webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/jwt/company-admin-activate")
						.queryParam("oktaUserId", userId)
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class)
				.returnResult()
				.getResponseBody();

		//assert
		assertAll(
				() -> assertThat(result).isNotNull(),
				() -> assertThat(JWT.decode(result).getSubject()).isEqualTo(userId)
		);
	}

	@Test
	@DisplayName("Should return company admin register token")
	void shouldReturnCompanyAdminRegisterToken() {

		//arrange
		final var email = "email@mail.com";

		//act
		final var result = webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/jwt/company-admin-register")
						.queryParam("email", email)
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class)
				.returnResult()
				.getResponseBody();

		//assert
		assertAll(
				() -> assertThat(result).isNotNull(),
				() -> assertThat(JWT.decode(result).getSubject()).isEqualTo(email)
		);
	}
}
