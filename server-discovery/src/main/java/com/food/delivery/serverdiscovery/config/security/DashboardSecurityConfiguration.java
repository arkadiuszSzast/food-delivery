package com.food.delivery.serverdiscovery.config.security;

import com.food.delivery.serverdiscovery.utils.properties.DashboardProperties;
import com.food.delivery.serverdiscovery.utils.security.DashboardAuthenticationProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Order(15)
@Configuration
@AllArgsConstructor
public class DashboardSecurityConfiguration extends SecurityConfiguration {

	private final DashboardProperties dashboardProperties;
	private final DashboardAuthenticationProvider basicAuthAuthenticationManager;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(basicAuthAuthenticationManager);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		configure(http, dashboardProperties);
	}
}
