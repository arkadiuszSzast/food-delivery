package com.food.delivery.mailsender.utils.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties("urls")
public class UrlsProperties {

	private final String userActivateUrl;
	private final String employeeActivateUrl;
	private final String companyAdminActivateUrl;
	private final String companyAdminRegisterUrl;

}
