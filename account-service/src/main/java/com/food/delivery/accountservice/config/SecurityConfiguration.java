package com.food.delivery.accountservice.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http
				.authorizeExchange()
				.pathMatchers("/v2/api-docs",
						"/configuration/ui",
						"/swagger-resources",
						"/swagger-resources/**",
						"/configuration/security",
						"/swagger-ui.html",
						"/webjars/**")
				.permitAll()
				.anyExchange().authenticated()
				.and()
				.oauth2ResourceServer()
				.jwt();

		Okta.configureResourceServer401ResponseBody(http);

		return http.build();
	}
}
