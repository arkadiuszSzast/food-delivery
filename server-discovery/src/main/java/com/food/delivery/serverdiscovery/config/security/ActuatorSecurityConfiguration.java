package com.food.delivery.serverdiscovery.config.security;

import com.food.delivery.serverdiscovery.utils.properties.ActuatorProperties;
import com.food.delivery.serverdiscovery.utils.security.ActuatorAuthenticationProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Order(10)
@Configuration
@AllArgsConstructor
public class ActuatorSecurityConfiguration extends SecurityConfiguration {

	private final ActuatorProperties actuatorProperties;
	private final ActuatorAuthenticationProvider basicAuthAuthenticationManager;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(basicAuthAuthenticationManager);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		configure(http, actuatorProperties);
	}
}
