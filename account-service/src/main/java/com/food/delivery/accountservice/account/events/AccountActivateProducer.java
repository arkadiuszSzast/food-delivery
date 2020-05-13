package com.food.delivery.accountservice.account.events;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountActivateProducer {

	private static final String ACTIVATE_USER_TOPIC = "activate-user";

	private final KafkaTemplate<String, AccountActivateEvent> kafkaTemplate;

	public void produceSendUserActivateEmail(AccountActivateEvent accountActivateEvent) {
		kafkaTemplate.send(ACTIVATE_USER_TOPIC, accountActivateEvent);
	}

}
