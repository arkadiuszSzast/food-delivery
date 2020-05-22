package com.food.delivery.gateway.config;

import com.food.delivery.gateway.config.security.LogoutHandler;
import com.food.delivery.gateway.token.BlacklistedTokenRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.WebFilterExchange;
import support.GatewayIntegrationTest;

import static org.assertj.core.api.Assertions.assertThat;

@GatewayIntegrationTest
class LogoutHandlerTestIT {

	@Autowired
	private LogoutHandler logoutHandler;
	@Autowired
	private BlacklistedTokenRepository blacklistedTokenRepository;
	@MockBean
	private WebFilterExchange webFilterExchange;

	@Test
	@DisplayName("Should add blacklisted token")
	void shouldAddBlacklistedToken() {
		//arrange
		final var jwt = Jwt.withTokenValue("empty")
				.header("", "")
				.claim("", "")
				.build();
		final var jwtAuthenticationToken = new JwtAuthenticationToken(jwt);

		//act
		logoutHandler.logout(webFilterExchange, jwtAuthenticationToken).block();

		//assert
		final var result = blacklistedTokenRepository.findAll().collectList().block();
		assertThat(result).hasSize(1);
	}

}
