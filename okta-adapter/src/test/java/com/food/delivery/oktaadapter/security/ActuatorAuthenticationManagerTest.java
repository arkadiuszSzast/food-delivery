package com.food.delivery.oktaadapter.security;

import com.food.delivery.oktaadapter.utils.properties.ActuatorProperties;
import com.food.delivery.oktaadapter.utils.security.ActuatorAuthenticationManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActuatorAuthenticationManagerTest {

	private final static String ACTUATOR_USERNAME = "actuator_account";
	private final static String ACTUATOR_PASSWORD = "password";
	private static final String ACTUATOR_INVALID_PASSWORD = "INVALID_PASSWORD";
	private static final String ACTUATOR_INVALID_USERNAME = "INVALID_USERNAME";
	private final static SimpleGrantedAuthority ACTUATOR_AUTHORITIES = new SimpleGrantedAuthority("ACTUATOR");

	@Mock
	private ActuatorProperties actuatorProperties;

	@InjectMocks
	private ActuatorAuthenticationManager actuatorAuthenticationManager;

	@Test
	@DisplayName("Should authenticate as actuator")
	void shouldAuthenticateAsActuator() {

		//arrange
		when(actuatorProperties.getUsername()).thenReturn(ACTUATOR_USERNAME);
		when(actuatorProperties.getPassword()).thenReturn(ACTUATOR_PASSWORD);
		when(actuatorProperties.getAuthority()).thenReturn(ACTUATOR_AUTHORITIES);

		//act
		final var authentication = new UsernamePasswordAuthenticationToken(actuatorProperties.getUsername(),
				actuatorProperties.getPassword(),
				Set.of(actuatorProperties.getAuthority()));
		final var result = actuatorAuthenticationManager.authenticate(authentication).block();

		//assert
		assertThat(result).isEqualTo(authentication);
	}

	@Test
	@DisplayName("Should not authenticate as actuator when invalid credentials")
	void shouldNotAuthenticateAsActuatorWhenInvalidCredentials() {

		//arrange
		when(actuatorProperties.getUsername()).thenReturn(ACTUATOR_USERNAME);
		when(actuatorProperties.getPassword()).thenReturn(ACTUATOR_PASSWORD);
		when(actuatorProperties.getAuthority()).thenReturn(ACTUATOR_AUTHORITIES);

		//act && assert
		final var authentication = new UsernamePasswordAuthenticationToken(actuatorProperties.getUsername(),
				ACTUATOR_INVALID_PASSWORD,
				Set.of(actuatorProperties.getAuthority()));
		assertThrows(BadCredentialsException.class,
				() -> actuatorAuthenticationManager.authenticate(authentication).block());
	}

	@Test
	@DisplayName("Should not authenticate as actuator when invalid username")
	void shouldNotAuthenticateAsActuatorWhenInvalidUsername() {

		//arrange
		when(actuatorProperties.getUsername()).thenReturn(ACTUATOR_USERNAME);
		when(actuatorProperties.getPassword()).thenReturn(ACTUATOR_PASSWORD);
		when(actuatorProperties.getAuthority()).thenReturn(ACTUATOR_AUTHORITIES);

		//act && assert
		final var authentication = new UsernamePasswordAuthenticationToken(ACTUATOR_INVALID_USERNAME,
				actuatorProperties.getPassword(),
				Set.of(actuatorProperties.getAuthority()));
		assertThrows(BadCredentialsException.class,
				() -> actuatorAuthenticationManager.authenticate(authentication).block());
	}
}
