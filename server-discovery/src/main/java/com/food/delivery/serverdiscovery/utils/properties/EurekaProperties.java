package com.food.delivery.serverdiscovery.utils.properties;

import com.food.delivery.serverdiscovery.utils.properties.type.AuthorityProperties;
import com.food.delivery.serverdiscovery.utils.properties.type.CredentialProperties;
import com.food.delivery.serverdiscovery.utils.properties.type.PathProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties("eureka")
public class EurekaProperties implements CredentialProperties, PathProperties, AuthorityProperties {

	private final String path;
	private final String username;
	private final String password;
	private final List<SimpleGrantedAuthority> authorities;

}
