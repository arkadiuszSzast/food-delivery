package com.food.delivery.accountservice.user.events;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserActivateProducer {

	private static final String ACTIVATE_USER_TOPIC = "activate-user";

	private final KafkaTemplate<String, UserActivateEvent> kafkaTemplate;

	public void produceSendUserActivateEmail(UserActivateEvent userActivateEvent) {
		kafkaTemplate.send(ACTIVATE_USER_TOPIC, userActivateEvent);
	}

}
