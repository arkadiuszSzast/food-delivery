package com.food.delivery.mailsender.utils.mail;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.food.delivery.mailsender.account.AccountActivateEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SendgridMail {

	private final Email from;
	private final Set<Personalizations> personalizations;
	private final String templateId;

	@JsonCreator
	public static SendgridMail aSendgridMail(@JsonProperty("accountActivateEvent") AccountActivateEvent accountActivateEvent) {
		final var email = accountActivateEvent.getEmail();
		final var personalizations = new Personalizations(Set.of(new Email(email)),
				Map.of("username", accountActivateEvent.getFirstName(),
						"confirm-url", "confirmURL"));
		return new SendgridMail(new Email("citini4306@mailop7.com"), Set.of(personalizations),
				"d-d00f99de8c8c4fe8b697c546a0bace78");
	}

}
