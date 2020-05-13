package com.food.delivery.oktaadapter.utils.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties("okta")
public class OktaProperties {

	private final String domainUrl;
	private final String apiToken;

}

