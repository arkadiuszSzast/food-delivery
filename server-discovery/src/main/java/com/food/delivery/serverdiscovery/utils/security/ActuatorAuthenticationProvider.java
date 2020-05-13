package com.food.delivery.serverdiscovery.utils.security;

import com.food.delivery.serverdiscovery.utils.properties.ActuatorProperties;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ActuatorAuthenticationProvider implements BasicAuthAuthenticationProvider {

	private final ActuatorProperties actuatorProperties;

	@Override
	public Authentication authenticate(Authentication authentication) {
		return authenticate(authentication, actuatorProperties);
	}

}
