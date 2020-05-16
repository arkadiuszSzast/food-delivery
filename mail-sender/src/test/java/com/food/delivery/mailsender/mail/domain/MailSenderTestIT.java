package com.food.delivery.mailsender.mail.domain;

import com.food.delivery.mailsender.mail.MailSender;
import com.food.delivery.mailsender.support.MailSenderIntegrationTest;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MailSenderIntegrationTest
class MailSenderTestIT {

	@Autowired
	private MailSender mailSender;

	@Test
	@DisplayName("Should send email")
	void shouldSendEmail() {
		//arrange
		final var from = new EmailWrapper("from@email.com");
		final var to = new EmailWrapper("to@email.com");
		final var personalization = Personalization.aPersonalization(to, Map.of());
		final var templateId = "d-d435f79c0b604a2dabb2d55e1cb9fda3";
		final var sendgridMail = new SendgridMail(from, Set.of(personalization), templateId);

		//act && assert
		assertDoesNotThrow(() -> mailSender.send(sendgridMail).block());
	}

	@Test
	@DisplayName("Should send not send email when wrong data provided")
	void shouldNotSendEmailWhenBadDataProvided() {
		//arrange
		final var from = new EmailWrapper("from@email.com");
		final var to = new EmailWrapper("to@email.com");
		final var personalization = Personalization.aPersonalization(to, Map.of());
		final var templateId = "wrongTemplateId";
		final var sendgridMail = new SendgridMail(from, Set.of(personalization), templateId);

		//act && assert
		assertThrows(HystrixRuntimeException.class,
				() -> mailSender.send(sendgridMail).block());
	}

}
