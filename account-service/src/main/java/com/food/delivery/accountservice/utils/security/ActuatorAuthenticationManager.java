package com.food.delivery.accountservice.utils.security;

import com.food.delivery.accountservice.utils.properties.ActuatorProperties;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@AllArgsConstructor
public class ActuatorAuthenticationManager implements ReactiveAuthenticationManager {

	private final ActuatorProperties actuatorProperties;

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {

		final var principal = authentication.getPrincipal();
		final var credentials = authentication.getCredentials();

		if (actuatorProperties.getUsername().equals(principal) && actuatorProperties.getPassword().equals(credentials)) {
			final var authorities = List.of(actuatorProperties.getAuthority());
			final var authenticationToken = new UsernamePasswordAuthenticationToken(principal, credentials, authorities);
			return Mono.just(authenticationToken);
		}

		throw new BadCredentialsException("Authentication failed for " + principal);
	}
}
