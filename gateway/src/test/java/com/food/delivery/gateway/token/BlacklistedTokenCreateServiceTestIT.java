package com.food.delivery.gateway.token;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import support.GatewayIntegrationTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@GatewayIntegrationTest
class BlacklistedTokenCreateServiceTestIT {

	@Autowired
	private BlacklistedTokenCreateService blacklistedTokenCreateService;
	@Autowired
	private BlacklistedTokenRepository blacklistedTokenRepository;

	@Test
	@DisplayName("Should add blacklisted token")
	void shouldAddBlacklistedToken() {
		//arrange
		final var jwt = Jwt.withTokenValue("empty")
				.header("", "")
				.claim("", "")
				.build();

		//act
		final var result = blacklistedTokenCreateService.create(jwt).block();

		//assert
		final var tokens = blacklistedTokenRepository.findAll().collectList().block();
		assertAll(
				() -> assertThat(tokens).usingElementComparatorIgnoringFields("createdDate").containsExactly(result),
				() -> assertThat(tokens.get(0).getCreatedDate()).isEqualToIgnoringSeconds(LocalDateTime.now())
		);
	}

}
