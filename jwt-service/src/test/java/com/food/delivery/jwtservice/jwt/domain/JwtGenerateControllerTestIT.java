package com.food.delivery.jwtservice.jwt.domain;

import com.auth0.jwt.JWT;
import com.food.delivery.jwtservice.support.JwtServiceIntegrationTest;
import com.food.delivery.jwtservice.utils.properties.jwt.ActivateAccountJwt;
import com.food.delivery.jwtservice.utils.properties.jwt.JwtProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@JwtServiceIntegrationTest
class JwtGenerateControllerTestIT {

	@Autowired
	private WebTestClient webTestClient;
	@MockBean
	private JwtProperties jwtProperties;

	@Test
	@DisplayName("Should return user activation token")
	void shouldReturnUserActivationToken() {

		//arrange
		final var activateAccountJwt = new ActivateAccountJwt("secret", "issuer", 86400000L);
		final var userId = "exampleUser";
		when(jwtProperties.getActivateAccount()).thenReturn(activateAccountJwt);

		//act
		final var result = webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/jwt/account-activate")
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
				() -> assertThat(JWT.decode(result).getSubject()).isEqualTo(userId),
				() -> assertThat(JWT.decode(result).getIssuer()).isEqualTo(activateAccountJwt.getIssuer())
		);
	}
}
