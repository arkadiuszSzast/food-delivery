package com.food.delivery.mailsender.mail.domain;

import com.food.delivery.mailsender.account.AccountActivateEvent;
import com.food.delivery.mailsender.account.AccountActivateUrlProvider;
import com.food.delivery.mailsender.jwt.JwtServiceClient;
import com.food.delivery.mailsender.utils.properties.SendgridProperties;
import com.food.delivery.mailsender.utils.properties.SendgridTemplatesProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SendgridMailFactoryTest {

	@Mock
	private SendgridProperties sendgridProperties;
	@Mock
	private SendgridTemplatesProperties sendgridTemplatesProperties;
	@Mock
	private JwtServiceClient jwtServiceClient;
	@Mock
	private AccountActivateUrlProvider accountActivateUrlProvider;
	@InjectMocks
	private SendgridMailFactory sendgridMailFactory;

	@Test
	@DisplayName("Should create user activation email")
	void shouldCreateUserActivationEmail() {
		//arrange
		final var firstName = "Joe";
		final var to = "joe@mail.com";
		final var oktaUserId = "oktaUserId";
		final var from = "sender@mail.com";
		final var confirmUserTemplateId = "confirmUserTemplateId";
		final var activateAccountJwt = "activateAccountJwt";
		final var activateUrl = "http://account-activate";
		final var accountActivateEvent = new AccountActivateEvent(firstName, to, oktaUserId);

		when(jwtServiceClient.getAccountActivateJwt(oktaUserId)).thenReturn(Mono.just(activateAccountJwt));
		when(accountActivateUrlProvider.getAccountActivateUrl(activateAccountJwt)).thenReturn(activateUrl);
		when(sendgridProperties.getSender()).thenReturn(from);
		when(sendgridTemplatesProperties.getConfirmUserRegistration()).thenReturn(confirmUserTemplateId);


		//act
		final var result = sendgridMailFactory.userActivateMail(accountActivateEvent).block();

		//assert
		final var personalization = Objects.requireNonNull(result).getPersonalizations().iterator().next();
		assertAll(
				() -> assertThat(result).isNotNull(),
				() -> assertThat(result.getFrom()).isEqualToComparingFieldByField(new EmailWrapper(from)),
				() -> assertThat(result.getTemplateId()).isEqualTo(confirmUserTemplateId),
				() -> assertThat(personalization.getTo().iterator().next()).isEqualToComparingFieldByField(new EmailWrapper(to)),
				() -> assertThat(personalization.getDynamicTemplateData()).hasSize(2),
				() -> assertThat(personalization.getDynamicTemplateData().get("username")).isEqualTo(firstName),
				() -> assertThat(personalization.getDynamicTemplateData().get("confirm-url")).isEqualTo(activateUrl)
		);
	}
}
