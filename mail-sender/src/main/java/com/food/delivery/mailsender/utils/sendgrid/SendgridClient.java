package com.food.delivery.mailsender.utils.sendgrid;

import com.food.delivery.mailsender.utils.mail.SendgridMail;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;


@ReactiveFeignClient(name = "sendgrid", url = "https://api.sendgrid.com/")
public interface SendgridClient {

	@PostMapping(value = "/v3/mail/send", produces = MediaType.APPLICATION_JSON_VALUE)
	Flux<Void> sendEmail(@RequestHeader("Authorization") String bearerToken, SendgridMail sendgridMail);
}
