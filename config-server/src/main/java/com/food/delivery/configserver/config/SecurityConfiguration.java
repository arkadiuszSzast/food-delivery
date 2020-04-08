package com.food.delivery.configserver.config;

import com.food.delivery.configserver.utils.properties.ActuatorProperties;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private final ActuatorProperties actuatorProperties;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers(actuatorProperties.getPath())
				.hasAuthority(actuatorProperties.getAuthorityName())
				.anyRequest().authenticated()
				.and()
				.httpBasic();
	}
}
