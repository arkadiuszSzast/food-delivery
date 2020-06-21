package com.food.delivery.mailsender.mail.domain;

import com.food.delivery.mailsender.account.AccountActivateUrlProvider;
import com.food.delivery.mailsender.account.CompanyAdminRegisterEvent;
import com.food.delivery.mailsender.account.EmployeeActivateEvent;
import com.food.delivery.mailsender.account.UserActivateEvent;
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

	public Mono<SendgridMail> getUserActivateMail(UserActivateEvent userActivateEvent) {
		final var template = sendgridTemplatesProperties.getConfirmUserRegistration();
		return jwtServiceClient.getUserActivateJwt(userActivateEvent.getOktaId())
				.map(accountActivateUrlProvider::getUserActivateUrl)
				.map(accountActivateUrl -> {
					final var dynamicTemplateData = Map.of(
							"username", userActivateEvent.getFirstName(),
							"confirm-url", accountActivateUrl);
					return sendAccountActivateEmail(userActivateEvent.getEmail(), template, dynamicTemplateData);
				});
	}

	public Mono<SendgridMail> getEmployeeActivateMail(EmployeeActivateEvent employeeActivateEvent) {
		final var userActivateEvent = employeeActivateEvent.getUserActivateEvent();
		final var template = sendgridTemplatesProperties.getConfirmEmployeeRegistration();
		return jwtServiceClient.getEmployeeActivateJwt(userActivateEvent.getOktaId())
				.map(accountActivateUrlProvider::getEmployeeActivateUrl)
				.map(accountActivateUrl -> {
					final var dynamicTemplateData = Map.of(
							"username", userActivateEvent.getFirstName(),
							"confirm-url", accountActivateUrl,
							"company-name", employeeActivateEvent.getCompanyName());
					return sendAccountActivateEmail(userActivateEvent.getEmail(), template, dynamicTemplateData);
				});
	}

	public Mono<SendgridMail> getCompanyAdminActivateMail(UserActivateEvent userActivateEvent) {
		final var template = sendgridTemplatesProperties.getConfirmCompanyAdminRegistration();
		return jwtServiceClient.getCompanyAdminActivateJwt(userActivateEvent.getOktaId())
				.map(accountActivateUrlProvider::getCompanyAdminActivateUrl)
				.map(accountActivateUrl -> {
					final var dynamicTemplateData = Map.of(
							"username", userActivateEvent.getFirstName(),
							"confirm-url", accountActivateUrl);
					return sendAccountActivateEmail(userActivateEvent.getEmail(), template, dynamicTemplateData);
				});
	}

	public Mono<SendgridMail> getCompanyAdminRegisterMail(CompanyAdminRegisterEvent companyAdminRegisterEvent) {
		final var template = sendgridTemplatesProperties.getCompanyAdminRegistration();
		return jwtServiceClient.getCompanyAdminRegisterJwt(companyAdminRegisterEvent.getEmail())
				.map(accountActivateUrlProvider::getCompanyAdminRegisterUrl)
				.map(companyAdminRegisterUrl -> {
					final var dynamicTemplateData = Map.of("company-admin-register-url", companyAdminRegisterUrl);
					return sendAccountActivateEmail(companyAdminRegisterEvent.getEmail(), template, dynamicTemplateData);
				});
	}

	private SendgridMail sendAccountActivateEmail(String recipient,
												  String template,
												  Map<String, String> dynamicTemplateData) {
		final var senderEmail = new EmailWrapper(sendgridProperties.getSender());
		final var recipientEmail = new EmailWrapper(recipient);
		final var personalizations = Personalization.aPersonalization(recipientEmail, dynamicTemplateData);
		return new SendgridMail(senderEmail, Set.of(personalizations), template);
	}
}
