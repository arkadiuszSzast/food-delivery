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

	private final ActivateAccountJwt activateAccount;

}
