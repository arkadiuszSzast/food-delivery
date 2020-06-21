package com.food.delivery.jwtservice.utils.properties.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties("jwt")
public class JwtProperties {

	private final BasicJwt activateUserJwt;
	private final BasicJwt activateEmployeeJwt;
	private final BasicJwt activateCompanyAdminJwt;
	private final BasicJwt registerCompanyAdminJwt;

}
