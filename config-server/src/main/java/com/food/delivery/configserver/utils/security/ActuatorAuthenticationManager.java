package com.food.delivery.configserver.utils.security;

import com.food.delivery.configserver.utils.properties.ActuatorProperties;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ActuatorAuthenticationManager implements AuthenticationManager {

	private final ActuatorProperties actuatorProperties;

	@Override
	public Authentication authenticate(Authentication authentication) {

		final var principal = authentication.getPrincipal();
		final var credentials = authentication.getCredentials();

		if (actuatorProperties.getUsername().equals(principal) && actuatorProperties.getPassword().equals(credentials)) {
			final var authorities = List.of(actuatorProperties.getAuthority());
			return new UsernamePasswordAuthenticationToken(principal, credentials, authorities);
		}

		return authentication;
	}
}
