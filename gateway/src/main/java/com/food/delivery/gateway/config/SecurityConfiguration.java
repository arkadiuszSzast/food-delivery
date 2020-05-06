package com.food.delivery.gateway.config;

import com.food.delivery.gateway.utils.properties.ActuatorProperties;
import com.okta.spring.boot.oauth.Okta;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

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
						"/webjars/**",
						"/**/v2/api-docs",
						"/account",
						"/account/activate")
				.permitAll()
				.anyExchange().hasAuthority("USER")
				.and()
				.oauth2ResourceServer().jwt();

		Okta.configureResourceServer401ResponseBody(http);

		return http.build();
	}

	@Bean
	CorsWebFilter corsWebFilter() {
		var corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(List.of("*"));
		corsConfig.setMaxAge(3600L);
		corsConfig.addAllowedMethod("*");
		corsConfig.addAllowedHeader("*");

		var source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);

		return new CorsWebFilter(source);
	}

}
