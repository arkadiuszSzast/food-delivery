package com.food.delivery.mailsender.utils.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties("sendgrid.template")
public class SendgridTemplatesProperties {

	private final String confirmUserRegistration;
	private final String confirmEmployeeRegistration;
	private final String confirmCompanyAdminRegistration;
	private final String companyAdminRegistration;

}
