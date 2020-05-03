package com.food.delivery.mailsender.utils.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties("sendgrid")
public class SendgridProperties {

	private final String url;
	private final String sender;
	private final String apiKey;

}
