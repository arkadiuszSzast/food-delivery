package com.food.delivery.accountservice.config;

import com.food.delivery.accountservice.utils.properties.ActuatorProperties;
import com.okta.spring.boot.oauth.Okta;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
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
				.pathMatchers("/v2/api-docs",
						"/configuration/ui",
						"/swagger-resources",
						"/swagger-resources/**",
						"/configuration/security",
						"/swagger-ui.html",
						"/webjars/**").permitAll()
				.pathMatchers(HttpMethod.POST, "/account/user",
						"/account/employee/company-admin").permitAll()
				.pathMatchers(HttpMethod.PATCH, "/account/user/activate",
						"/account/employee/company-admin/activate").permitAll()
				.pathMatchers(HttpMethod.PATCH, "/account/employee/activate").permitAll()
				.anyExchange().hasAnyAuthority("USER", "COMPANY_EMPLOYEE")
				.and()
				.oauth2ResourceServer()
				.jwt();

		Okta.configureResourceServer401ResponseBody(http);

		return http.build();
	}
}
