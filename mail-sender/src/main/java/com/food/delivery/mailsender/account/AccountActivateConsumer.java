package com.food.delivery.mailsender.account;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class AccountActivateConsumer {

	private static final String ACTIVATE_USER = "activate-user";

	@KafkaListener(id = "activate-user-consumer", topicPattern = ACTIVATE_USER)
	public void consumeActivateUser(@Payload AccountActivateEvent accountActivateEvent) {
		System.out.println("Got message: " + accountActivateEvent.toString());
	}
}
