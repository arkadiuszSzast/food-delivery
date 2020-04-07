package com.food.delivery.oktaadapter.config;

import com.food.delivery.oktaadapter.utils.properties.ActuatorProperties;
import com.okta.spring.boot.oauth.Okta;
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

		http.securityMatcher(ServerWebExchangeMatchers.pathMatchers(actuatorProperties.getPath()))
				.authorizeExchange()
				.pathMatchers(actuatorProperties.getPath())
				.hasAuthority(actuatorProperties.getAuthorityName())
				.and()
				.httpBasic();

		http
				.authorizeExchange()
				.anyExchange().permitAll()
				.and()
				.oauth2ResourceServer()
				.jwt();

		Okta.configureResourceServer401ResponseBody(http);

		return http.build();
	}
}
