package com.food.delivery.mailsender.mail.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

@Getter
@AllArgsConstructor
public class Personalization {

	private final Set<Email> to;
	private final Map<String, Object> dynamicTemplateData;

	public static Personalization aPersonalization(Email to, Map<String, Object> dynamicTemplateData) {
		return new Personalization(Set.of(to), dynamicTemplateData);
	}

}
