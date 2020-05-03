package com.food.delivery.mailsender.mail;

import com.food.delivery.mailsender.mail.domain.SendgridMail;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
@AllArgsConstructor
public class MailSender {

	private final SendgridClient sendgridClient;

	public Flux<Void> send(SendgridMail mail) {
		return sendgridClient.sendEmail(mail);
	}

}
