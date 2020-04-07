package com.food.delivery.accountservice.utils.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@Setter
@ConfigurationProperties("actuator")
public class ActuatorProperties {

	private String path;
	private String username;
	private String password;
	private SimpleGrantedAuthority authority;

	public String getAuthorityName() {
		return authority.getAuthority();
	}
}
