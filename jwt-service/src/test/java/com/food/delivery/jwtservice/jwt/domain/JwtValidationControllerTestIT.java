package com.food.delivery.jwtservice.jwt.domain;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.food.delivery.jwtservice.jwt.JwtGenerateService;
import com.food.delivery.jwtservice.support.JwtServiceIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@JwtServiceIntegrationTest
class JwtValidationControllerTestIT {

	@Autowired
	private WebTestClient webTestClient;
	@Autowired
	private JwtGenerateService jwtGenerateService;

	@Test
	@DisplayName("Should return 200 when activate user token is valid")
	void shouldReturn200WhenValidActivateUserToken() {

		//arrange
		final var userId = "exampleUser";
		final var token = jwtGenerateService.getUserActivateJwt(userId);

		//act && assert
		webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/jwt/validations/user-activate")
						.queryParam("token", token)
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class).isEqualTo(userId);
	}

	@Test
	@DisplayName("Should return 401 when invalid activate user token is given")
	void shouldReturn404WhenInvalidActivateUserToken() {

		//arrange
		final var invalidToken = JWT.create().sign(Algorithm.none());

		//act && assert
		webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/jwt/validations/user-activate")
						.queryParam("token", invalidToken)
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isUnauthorized();
	}

	@Test
	@DisplayName("Should return 200 when activate employee token is valid")
	void shouldReturn200WhenValidActivateEmployeeToken() {

		//arrange
		final var userId = "exampleUser";
		final var token = jwtGenerateService.getEmployeeActivateJwt(userId);

		//act && assert
		webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/jwt/validations/employee-activate")
						.queryParam("token", token)
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class).isEqualTo(userId);
	}

	@Test
	@DisplayName("Should return 401 when invalid activate employee token is given")
	void shouldReturn404WhenInvalidActivateEmployeeToken() {

		//arrange
		final var invalidToken = JWT.create().sign(Algorithm.none());

		//act && assert
		webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/jwt/validations/employee-activate")
						.queryParam("token", invalidToken)
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isUnauthorized();
	}

	@Test
	@DisplayName("Should return 200 when activate company admin token is valid")
	void shouldReturn200WhenValidActivateCompanyAdminToken() {

		//arrange
		final var userId = "exampleUser";
		final var token = jwtGenerateService.getCompanyAdminActivateJwt(userId);

		//act && assert
		webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/jwt/validations/company-admin-activate")
						.queryParam("token", token)
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class).isEqualTo(userId);
	}

	@Test
	@DisplayName("Should return 401 when invalid activate company admin token is given")
	void shouldReturn404WhenInvalidActivateCompanyAdminToken() {

		//arrange
		final var invalidToken = JWT.create().sign(Algorithm.none());

		//act && assert
		webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/jwt/validations/company-admin-activate")
						.queryParam("token", invalidToken)
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isUnauthorized();
	}

	@Test
	@DisplayName("Should return 200 when register company admin token is valid")
	void shouldReturn200WhenValidRegisterCompanyAdminToken() {

		//arrange
		final var userId = "exampleUser";
		final var token = jwtGenerateService.getCompanyAdminRegisterJwt(userId);

		//act && assert
		webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/jwt/validations/company-admin-register")
						.queryParam("token", token)
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class).isEqualTo(userId);
	}

	@Test
	@DisplayName("Should return 401 when invalid register company admin token is given")
	void shouldReturn404WhenInvalidRegisterCompanyAdminToken() {

		//arrange
		final var invalidToken = JWT.create().sign(Algorithm.none());

		//act && assert
		webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/jwt/validations/company-admin-register")
						.queryParam("token", invalidToken)
						.build())
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isUnauthorized();
	}

}
