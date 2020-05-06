package com.food.delivery.mailsender.mail.domain;

import com.food.delivery.mailsender.account.AccountActivateEvent;
import com.food.delivery.mailsender.account.AccountActivateUrlProvider;
import com.food.delivery.mailsender.jwt.JwtServiceClient;
import com.food.delivery.mailsender.utils.properties.SendgridProperties;
import com.food.delivery.mailsender.utils.properties.SendgridTemplatesProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;

@Component
@AllArgsConstructor
public class SendgridMailFactory {

	private final SendgridProperties sendgridProperties;
	private final SendgridTemplatesProperties sendgridTemplatesProperties;
	private final JwtServiceClient jwtServiceClient;
	private final AccountActivateUrlProvider accountActivateUrlProvider;

	public Mono<SendgridMail> userActivateMail(AccountActivateEvent accountActivateEvent) {
		return jwtServiceClient.getAccountActivateJwt(accountActivateEvent.getOktaId())
				.map(accountActivateUrlProvider::getAccountActivateUrl)
				.map(accountActivateUrl -> {
					final var template = sendgridTemplatesProperties.getConfirmUserRegistration();
					final var sender = new Email(sendgridProperties.getSender());
					final var recipient = new Email(accountActivateEvent.getEmail());
					final var dynamicTemplateData = Map.of(
							"username", accountActivateEvent.getFirstName(),
							"confirm-url", accountActivateUrl);
					final var personalizations = Personalization.aPersonalization(recipient, dynamicTemplateData);
					return new SendgridMail(sender, Set.of(personalizations), template);
				});
	}
}
