package com.food.delivery.mailsender.mail;

import com.food.delivery.mailsender.mail.domain.SendgridMail;
import org.springframework.web.bind.annotation.PostMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;


@ReactiveFeignClient(name = "sendgrid", url = "${sendgrid.url}")
public interface SendgridClient {

	@PostMapping(value = "/v3/mail/send")
	Mono<Void> sendEmail(SendgridMail sendgridMail);
}
