package com.food.delivery.jwtservice.jwt;

import com.auth0.jwt.JWT;
import com.food.delivery.jwtservice.utils.properties.jwt.BasicJwt;
import com.food.delivery.jwtservice.utils.properties.jwt.JwtProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtGenerateServiceTest {

	@Mock
	private JwtProperties jwtProperties;
	@InjectMocks
	private JwtGenerateService jwtGenerateService;

	@Test
	@DisplayName("Should generate token")
	void shouldGenerateToken() {
		//arrange
		final var secret = "Bm30lw0I";
		final var expirationTime = 86400000L;
		final var issuer = "issuer";
		final var userId = "oktaUserId";
		when(jwtProperties.getActivateUserJwt()).thenReturn(new BasicJwt(secret, issuer, expirationTime));

		//act && assert
		final var result = jwtGenerateService.getUserActivateJwt(userId);
		assertAll(
				() -> assertThat(result).isNotNull(),
				() -> assertThat(JWT.decode(result).getSubject()).isEqualTo(userId),
				() -> assertThat(JWT.decode(result).getIssuer()).isEqualTo(issuer)
		);
	}
}
