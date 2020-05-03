package com.food.delivery.mailsender.account;

import com.food.delivery.mailsender.utils.mail.MailSender;
import com.food.delivery.mailsender.utils.mail.SendgridMail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AccountActivateConsumer {

	private static final String ACTIVATE_USER = "activate-user";

	private final MailSender mailSender;

	@KafkaListener(id = "activate-user-consumer", topicPattern = ACTIVATE_USER)
	public void consumeActivateUser(@Payload AccountActivateEvent accountActivateEvent) {
		final var sendgridMail = SendgridMail.aSendgridMail(accountActivateEvent);
		mailSender.send(sendgridMail).subscribe(data -> log.info("Mail sent successfully"));
		System.out.println("Got message: " + accountActivateEvent.toString());
	}
}
