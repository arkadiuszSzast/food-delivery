package com.food.delivery.mailsender.mail.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SendgridMail {

	private final EmailWrapper from;
	private final Set<Personalization> personalizations;
	private final String templateId;

}
