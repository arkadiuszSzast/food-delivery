package com.food.delivery.jwtservice.utils.security;

import com.food.delivery.jwtservice.support.JwtServiceIntegrationTest;
import com.food.delivery.jwtservice.utils.properties.ActuatorProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@JwtServiceIntegrationTest
class ActuatorAuthenticationManagerTest {

	private static final String ACTUATOR_WRONG_PASSWORD = "BAD_PASSWORD";

	@Autowired
	private ActuatorAuthenticationManager actuatorAuthenticationManager;
	@Autowired
	private ActuatorProperties actuatorProperties;

	@Test
	@DisplayName("Should authenticate as actuator")
	void shouldAuthenticateAsActuator() {

		final var authentication = new UsernamePasswordAuthenticationToken(actuatorProperties.getUsername(),
				actuatorProperties.getPassword(),
				Set.of(actuatorProperties.getAuthority()));

		final var result = actuatorAuthenticationManager.authenticate(authentication).block();

		assertThat(result).isEqualTo(authentication);
	}

	@Test
	@DisplayName("Should not authenticate as actuator when bad credentials given")
	void shouldNotAuthenticateAsActuator() {

		final var authentication = new UsernamePasswordAuthenticationToken(actuatorProperties.getUsername(),
				ACTUATOR_WRONG_PASSWORD,
				Set.of(actuatorProperties.getAuthority()));

		assertThrows(BadCredentialsException.class,
				() -> actuatorAuthenticationManager.authenticate(authentication).block());
	}

}
