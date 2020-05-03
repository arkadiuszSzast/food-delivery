package com.food.delivery.mailsender.utils.mail;

import com.food.delivery.mailsender.utils.sendgrid.SendgridClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;


@Service
@Slf4j
public class MailSender {

	private final String apiKey;
	private final String url;
	private final SendgridClient sendgridClient;

	public MailSender(@Value("${sendgrid.apikey}") String apiKey,
					  @Value("${sendgrid.url}") String url,
					  SendgridClient sendgridClient) {
		this.apiKey = apiKey;
		this.url = url;
		this.sendgridClient = sendgridClient;
	}

	public Flux<Void> send(SendgridMail mail) {
		return sendgridClient.sendEmail("Bearer " + apiKey, mail);
	}

}
