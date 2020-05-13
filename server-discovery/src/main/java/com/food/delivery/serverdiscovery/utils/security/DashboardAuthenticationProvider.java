package com.food.delivery.serverdiscovery.utils.security;

import com.food.delivery.serverdiscovery.utils.properties.DashboardProperties;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DashboardAuthenticationProvider implements BasicAuthAuthenticationProvider {

	private final DashboardProperties dashboardProperties;

	@Override
	public Authentication authenticate(Authentication authentication) {
		return authenticate(authentication, dashboardProperties);
	}

}
