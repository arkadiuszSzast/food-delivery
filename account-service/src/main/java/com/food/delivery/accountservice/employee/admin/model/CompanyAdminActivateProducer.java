package com.food.delivery.accountservice.employee.admin.model;

import com.food.delivery.accountservice.user.events.UserActivateEvent;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyAdminActivateProducer {

	private static final String ACTIVATE_COMPANY_ADMIN_TOPIC = "activate-company-admin";

	private final KafkaTemplate<String, UserActivateEvent> kafkaTemplate;

	public void produceSendCompanyAdminActivateEmail(UserActivateEvent userActivateEvent) {
		kafkaTemplate.send(ACTIVATE_COMPANY_ADMIN_TOPIC, userActivateEvent);
	}

}
