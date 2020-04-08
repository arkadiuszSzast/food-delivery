package com.food.delivery.serverdiscovery.utils.properties.type;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public interface AuthorityProperties {

	List<SimpleGrantedAuthority> getAuthorities();

	default String[] getAuthorityNamesAsArray() {
		return getAuthorities().stream()
				.map(SimpleGrantedAuthority::getAuthority)
				.toArray(String[]::new);
	}

}
