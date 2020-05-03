package com.food.delivery.mailsender.account;

import com.food.delivery.mailsender.mail.MailSender;
import com.food.delivery.mailsender.mail.domain.SendgridMailFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AccountActivateConsumer {

	private static final String TOPIC_ACTIVATE_USER = "activate-user";

	private final MailSender mailSender;
	private final SendgridMailFactory sendgridMailFactory;

	@KafkaListener(id = "activate-user-consumer", topicPattern = TOPIC_ACTIVATE_USER)
	public void consumeActivateUser(@Payload AccountActivateEvent accountActivateEvent) {
		final var sendgridMail = sendgridMailFactory.userActivateMail(accountActivateEvent);
		mailSender.send(sendgridMail).subscribe(data -> log.info("Mail sent successfully"));
	}
}
