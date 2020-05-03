package com.food.delivery.mailsender.mail;

import com.food.delivery.mailsender.support.MailSenderIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@MailSenderIntegrationTest
class MailSenderTest {

	@Autowired
	private MailSender mailSender;

	@Test
	void shouldSendActivationEmail() {
//		final var accountActivateEvent = new AccountActivateEvent("Joe", "test@test.com");
//		final var sendgridMail = SendgridMail.aSendgridMail(accountActivateEvent);
//		mailSender.send(sendgridMail).subscribe();
	}

}

