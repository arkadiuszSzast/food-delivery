package com.food.delivery.mailsender.account;

import com.food.delivery.mailsender.mail.MailSender;
import com.food.delivery.mailsender.mail.domain.SendgridMail;
import com.food.delivery.mailsender.mail.domain.SendgridMailFactory;
import com.food.delivery.mailsender.support.MailSenderIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;


@MailSenderIntegrationTest
class AccountActivateConsumerTestIT {

	@Mock
	private SendgridMailFactory sendgridMailFactory;
	@Mock
	private MailSender mailSender;
	@Mock
	private SendgridMail sendgridMail;
	@InjectMocks
	private AccountActivateConsumer accountActivateConsumer;

	@Test
	@DisplayName("Should send user activation mail")
	void shouldSendUserActivationMail() {
		//arrange
		final var firstName = "Joe";
		final var to = "joe@mail.com";
		final var oktaId = "oktaId";
		final var accountActivateEvent = new UserActivateEvent(firstName, to, oktaId);
		when(sendgridMailFactory.getUserActivateMail(accountActivateEvent)).thenReturn(Mono.just(sendgridMail));
		when(mailSender.send(sendgridMail)).thenReturn(Mono.fromRunnable(System.out::println));

		//act
		accountActivateConsumer.consumeActivateUser(accountActivateEvent);

		//assert
		verify(sendgridMailFactory, only()).getUserActivateMail(accountActivateEvent);
		verify(mailSender, only()).send(sendgridMail);
	}

}
