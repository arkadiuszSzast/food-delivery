package com.food.delivery.oktaadapter.security;

import com.food.delivery.oktaadapter.support.OktaAdapterIntegrationTest;
import com.food.delivery.oktaadapter.utils.properties.ActuatorProperties;
import com.food.delivery.oktaadapter.utils.security.ActuatorAuthenticationManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@OktaAdapterIntegrationTest
class ActuatorAuthenticationManagerTestIT {
	private static final String ACTUATOR_WRONG_PASSWORD = "BAD_PASSWORD";
	private static final String ACTUATOR_INVALID_USERNAME = "INVALID_USERNAME";

	@Autowired
	private ActuatorAuthenticationManager actuatorAuthenticationManager;
	@Autowired
	private ActuatorProperties actuatorProperties;

	@Test
	@DisplayName("Should authenticate as actuator")
	void shouldAuthenticateAsActuator() {

		//arrange
		final var authentication = new UsernamePasswordAuthenticationToken(actuatorProperties.getUsername(),
				actuatorProperties.getPassword(),
				Set.of(actuatorProperties.getAuthority()));

		//act
		final var result = actuatorAuthenticationManager.authenticate(authentication).block();

		//assert
		assertThat(result).isEqualTo(authentication);
	}

	@Test
	@DisplayName("Should not authenticate as actuator when invalid credentials")
	void shouldNotAuthenticateAsActuatorWhenInvalidCredentials() {

		//arrange
		final var authentication = new UsernamePasswordAuthenticationToken(actuatorProperties.getUsername(),
				ACTUATOR_WRONG_PASSWORD,
				Set.of(actuatorProperties.getAuthority()));

		//act && assert
		assertThrows(BadCredentialsException.class,
				() -> actuatorAuthenticationManager.authenticate(authentication).block());
	}

	@Test
	@DisplayName("Should not authenticate as actuator when invalid username")
	void shouldNotAuthenticateAsActuatorWhenInvalidUsername() {

		//arrange
		final var authentication = new UsernamePasswordAuthenticationToken(ACTUATOR_INVALID_USERNAME,
				actuatorProperties.getPassword(),
				Set.of(actuatorProperties.getAuthority()));

		//act && assert
		assertThrows(BadCredentialsException.class,
				() -> actuatorAuthenticationManager.authenticate(authentication).block());
	}

}
