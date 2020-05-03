package com.food.delivery.mailsender.utils.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
public class Personalizations {

	private Set<Email> to;
	private Map<String, Object> dynamicTemplateData;

}
