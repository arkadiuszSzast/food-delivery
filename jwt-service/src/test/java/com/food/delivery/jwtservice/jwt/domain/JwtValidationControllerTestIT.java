package com.food.delivery.jwtservice.jwt.domain;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.food.delivery.jwtservice.jwt.JwtGenerateService;
import com.food.delivery.jwtservice.support.JwtServiceIntegrationTest;
import com.food.delivery.jwtservice.utils.properties.jwt.BasicJwt;
import com.food.delivery.jwtservice.utils.properties.jwt.JwtProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.Mockito.when;

@JwtServiceIntegrationTest
class JwtValidationControllerTestIT {

	@Autowired
	private WebTestClient webTestClient;
	@Autowired
	private JwtGenerateService jwtGenerateService;
	@MockBean
	private JwtProperties jwtProperties;

	@Test
	@DisplayName("Should return 200 when token is valid")
	void shouldReturn200WhenValidToken() {

		//arrange
		final var activateAccountJwt = new BasicJwt("secret", "issuer", 86400000L);
		final var userId = "exampleUser";
		when(jwtProperties.getActivateUserJwt()).thenReturn(activateAccountJwt);

		//act && assert
		final var token = jwtGenerateService.getUserActivateJwt(userId);
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
	@DisplayName("Should return 401 when invalid token is given")
	void shouldReturn404WhenInvalidToken() {

		//arrange
		final var activateAccountJwt = new BasicJwt("secret", "issuer", 86400000L);
		final var invalidToken = JWT.create().sign(Algorithm.none());
		when(jwtProperties.getActivateUserJwt()).thenReturn(activateAccountJwt);

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

}
