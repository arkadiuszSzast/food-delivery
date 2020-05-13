package com.food.delivery.mailsender.mail.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

@Getter
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Personalization {

	private final Set<EmailWrapper> to;
	private final Map<String, String> dynamicTemplateData;

	public static Personalization aPersonalization(EmailWrapper to, Map<String, String> dynamicTemplateData) {
		return new Personalization(Set.of(to), dynamicTemplateData);
	}

}
