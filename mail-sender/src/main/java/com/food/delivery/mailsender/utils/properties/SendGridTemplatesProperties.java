package com.food.delivery.mailsender.utils.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("mail.templates")
public class SendGridTemplatesProperties {

	private String confirmUserRegistration;
	private String confirmEmployeeRegistration;

}
