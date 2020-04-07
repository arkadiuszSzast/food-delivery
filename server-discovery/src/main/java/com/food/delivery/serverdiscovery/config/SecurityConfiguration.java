package com.food.delivery.serverdiscovery.config;

import com.food.delivery.serverdiscovery.utils.properties.ActuatorProperties;
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
				.csrf().disable()
				.authorizeRequests()
//				.antMatchers("/eureka/").permitAll()
//				.antMatchers(actuatorProperties.getPath())
//				.hasAuthority(actuatorProperties.getAuthorityName())
				.anyRequest().permitAll()
				.and()
				.httpBasic();
	}
}
