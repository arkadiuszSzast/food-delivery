package com.food.delivery.serverdiscovery.config.security;

import com.food.delivery.serverdiscovery.utils.properties.EurekaProperties;
import com.food.delivery.serverdiscovery.utils.security.EurekaAuthenticationProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Order(5)
@Configuration
@AllArgsConstructor
public class EurekaSecurityConfiguration extends SecurityConfiguration {

	private final EurekaProperties eurekaProperties;
	private final EurekaAuthenticationProvider basicAuthAuthenticationManager;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(basicAuthAuthenticationManager);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		configure(http, eurekaProperties);
	}

}
