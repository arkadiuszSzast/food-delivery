package com.food.delivery.mailsender.mail.domain;

import com.food.delivery.mailsender.account.AccountActivateEvent;
import com.food.delivery.mailsender.utils.properties.SendgridProperties;
import com.food.delivery.mailsender.utils.properties.SendgridTemplatesProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
@AllArgsConstructor
public class SendgridMailFactory {

	private final SendgridProperties sendgridProperties;
	private final SendgridTemplatesProperties sendgridTemplatesProperties;

	public SendgridMail userActivateMail(AccountActivateEvent accountActivateEvent) {
		final var template = sendgridTemplatesProperties.getConfirmUserRegistration();
		final var sender = new Email(sendgridProperties.getSender());
		final var recipient = new Email(accountActivateEvent.getEmail());
		final var dynamicTemplateData = Map.<String, Object>of(
				"username", accountActivateEvent.getFirstName(),
				"confirm-url", "confirmURL");
		final var personalizations = Personalization.aPersonalization(recipient, dynamicTemplateData);

		return new SendgridMail(sender, Set.of(personalizations), template);
	}
}
