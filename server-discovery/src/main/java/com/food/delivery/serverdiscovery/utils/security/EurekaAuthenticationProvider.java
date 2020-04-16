package com.food.delivery.serverdiscovery.utils.security;

import com.food.delivery.serverdiscovery.utils.properties.EurekaProperties;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EurekaAuthenticationProvider implements BasicAuthAuthenticationProvider {

	private final EurekaProperties eurekaProperties;

	@Override
	public Authentication authenticate(Authentication authentication) {
		return authenticate(authentication, eurekaProperties);
	}

}
