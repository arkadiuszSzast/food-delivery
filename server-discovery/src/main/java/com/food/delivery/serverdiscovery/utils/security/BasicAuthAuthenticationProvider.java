package com.food.delivery.serverdiscovery.utils.security;

import com.food.delivery.serverdiscovery.utils.properties.type.AuthorityProperties;
import com.food.delivery.serverdiscovery.utils.properties.type.CredentialProperties;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


interface BasicAuthAuthenticationProvider extends AuthenticationProvider {

	default <T extends CredentialProperties & AuthorityProperties> Authentication authenticate(Authentication authentication,
																							   T properties) {
		final var principal = authentication.getPrincipal();
		final var credentials = authentication.getCredentials();

		if (properties.getUsername().equals(principal)
				&& properties.getPassword().equals(credentials)) {

			return new UsernamePasswordAuthenticationToken(principal, credentials,
					properties.getAuthorities());
		}

		throw new BadCredentialsException("Authentication failed for " + principal);
	}

	@Override
	default boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
