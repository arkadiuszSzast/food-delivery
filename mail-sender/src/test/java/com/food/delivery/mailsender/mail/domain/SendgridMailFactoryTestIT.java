package com.food.delivery.mailsender.mail.domain;

import com.food.delivery.mailsender.account.UserActivateEvent;
import com.food.delivery.mailsender.jwt.JwtServiceClient;
import com.food.delivery.mailsender.support.MailSenderIntegrationTest;
import com.food.delivery.mailsender.utils.properties.SendgridProperties;
import com.food.delivery.mailsender.utils.properties.SendgridTemplatesProperties;
import com.food.delivery.mailsender.utils.properties.UrlsProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@MailSenderIntegrationTest
class SendgridMailFactoryTestIT {

	@Autowired
	private SendgridMailFactory sendgridMailFactory;
	@Autowired
	private SendgridTemplatesProperties sendgridTemplatesProperties;
	@Autowired
	private SendgridProperties sendgridProperties;
	@Autowired
	private UrlsProperties urlsProperties;
	@MockBean
	private JwtServiceClient jwtServiceClient;

	@Test
	@DisplayName("Should create user activation email")
	void shouldCreateUserActivationEmail() {
		//arrange
		final var firstName = "Joe";
		final var to = "joe@mail.com";
		final var oktaUserId = "oktaUserId";
		final var activateAccountJwt = "activateAccountJwt";
		final var accountActivateEvent = new UserActivateEvent(firstName, to, oktaUserId);

		when(jwtServiceClient.getUserActivateJwt(oktaUserId)).thenReturn(Mono.just(activateAccountJwt));

		//act
		final var result = sendgridMailFactory.getUserActivateMail(accountActivateEvent).block();

		//assert
		final var personalization = Objects.requireNonNull(result).getPersonalizations().iterator().next();
		assertAll(
				() -> assertThat(result).isNotNull(),
				() -> assertThat(result.getFrom())
						.isEqualToComparingFieldByField(new EmailWrapper(sendgridProperties.getSender())),
				() -> assertThat(result.getTemplateId())
						.isEqualTo(sendgridTemplatesProperties.getConfirmUserRegistration()),
				() -> assertThat(personalization.getTo().iterator().next())
						.isEqualToComparingFieldByField(new EmailWrapper(to)),
				() -> assertThat(personalization.getDynamicTemplateData())
						.hasSize(2),
				() -> assertThat(personalization.getDynamicTemplateData().get("username"))
						.isEqualTo(firstName),
				() -> assertThat(personalization.getDynamicTemplateData().get("confirm-url"))
						.contains(urlsProperties.getUserActivateUrl(), activateAccountJwt)
		);

	}

}
