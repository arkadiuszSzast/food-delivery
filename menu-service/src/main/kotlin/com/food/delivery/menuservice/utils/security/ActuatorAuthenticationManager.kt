package com.food.delivery.menuservice.utils.security

import com.food.delivery.menuservice.utils.properties.ActuatorProperties
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class ActuatorAuthenticationManager(private val actuatorProperties: ActuatorProperties) : ReactiveAuthenticationManager {

	override fun authenticate(authentication: Authentication): Mono<Authentication> {
		val principal = authentication.principal
		val credentials = authentication.credentials
		if (actuatorProperties.username == principal && actuatorProperties.password == credentials) {
			val authorities: List<SimpleGrantedAuthority> = listOf(actuatorProperties.authority)
			val authenticationToken = UsernamePasswordAuthenticationToken(principal, credentials, authorities)
			return Mono.just(authenticationToken)
		}
		throw BadCredentialsException("Authentication failed for $principal")
	}
}
