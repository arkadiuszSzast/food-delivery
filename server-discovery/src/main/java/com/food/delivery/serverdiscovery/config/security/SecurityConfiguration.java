package com.food.delivery.serverdiscovery.config.security;

import com.food.delivery.serverdiscovery.utils.properties.type.AuthorityProperties;
import com.food.delivery.serverdiscovery.utils.properties.type.PathProperties;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public abstract class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	protected <T extends PathProperties & AuthorityProperties> void configure(HttpSecurity http,
																			  T properties) throws Exception {
		http
				.csrf().disable()
				.requestMatchers().antMatchers(properties.getPath())
				.and()
				.authorizeRequests().antMatchers(properties.getPath())
				.hasAnyAuthority(properties.getAuthorityNamesAsArray())
				.anyRequest().denyAll()
				.and()
				.httpBasic();
	}
}
