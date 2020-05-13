package com.food.delivery.companyservice.utils.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties("actuator")
public class ActuatorProperties {

	private final String path;
	private final String username;
	private final String password;
	private final SimpleGrantedAuthority authority;

	public String getAuthorityName() {
		return authority.getAuthority();
	}
}
