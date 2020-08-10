package com.food.delivery.menuservice.config

import com.food.delivery.menuservice.utils.properties.ActuatorProperties
import com.okta.spring.boot.oauth.Okta
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfiguration(private val actuatorProperties: ActuatorProperties) {

	@Bean
	fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
		http
				.securityMatcher(ServerWebExchangeMatchers.pathMatchers(actuatorProperties.path))
				.authorizeExchange()
				.pathMatchers(actuatorProperties.path)
				.hasAuthority(actuatorProperties.getAuthorityName())
				.and()
				.httpBasic()
        http
                .securityMatcher(ServerWebExchangeMatchers.pathMatchers("/**"))
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/v3/api-docs")
                .permitAll()
                .pathMatchers(HttpMethod.GET, "/menu")
				.permitAll()
				.anyExchange().hasAnyAuthority("USER", "COMPANY_EMPLOYEE")
				.and()
				.oauth2ResourceServer()
				.jwt()
		Okta.configureResourceServer401ResponseBody(http)
		return http.build()
	}
}
