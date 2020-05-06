package com.food.delivery.jwtservice.config;

import com.food.delivery.jwtservice.utils.properties.ActuatorProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

@AllArgsConstructor
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

	private final ActuatorProperties actuatorProperties;

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

		http
				.securityMatcher(ServerWebExchangeMatchers.pathMatchers(actuatorProperties.getPath()))
				.authorizeExchange()
				.pathMatchers(actuatorProperties.getPath())
				.hasAuthority(actuatorProperties.getAuthorityName())
				.and()
				.httpBasic();

		http
				.securityMatcher(ServerWebExchangeMatchers.pathMatchers("/**"))
				.csrf().disable()
				.authorizeExchange()
				.anyExchange().permitAll();

		return http.build();
	}
}
