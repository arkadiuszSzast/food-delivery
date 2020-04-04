package com.food.delivery.oktaadapter.utils.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("okta")
public class OktaProperties {

	private String domainUrl;
	private String apiToken;

}

