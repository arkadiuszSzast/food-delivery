package com.food.delivery.menuservice.utils.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.security.core.authority.SimpleGrantedAuthority

@ConstructorBinding
@ConfigurationProperties("actuator")
data class ActuatorProperties(val path: String, val username: String, val password: String,
							  val authority: SimpleGrantedAuthority) {

	fun getAuthorityName(): String {
		return authority.authority
	}
}
