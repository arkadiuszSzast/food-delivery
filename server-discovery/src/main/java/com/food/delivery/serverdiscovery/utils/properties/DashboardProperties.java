package com.food.delivery.serverdiscovery.utils.properties;

import com.food.delivery.serverdiscovery.utils.properties.type.AuthorityProperties;
import com.food.delivery.serverdiscovery.utils.properties.type.CredentialProperties;
import com.food.delivery.serverdiscovery.utils.properties.type.PathProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties("dashboard")
public class DashboardProperties implements CredentialProperties, PathProperties, AuthorityProperties {

	private String path;
	private String username;
	private String password;
	private List<SimpleGrantedAuthority> authorities;

}
