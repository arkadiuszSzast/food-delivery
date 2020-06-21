package com.food.delivery.accountservice.employee.events;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyAdminRegisterProducer {

	private static final String TOPIC_COMPANY_ADMIN_REGISTER = "company-admin-register";

	private final KafkaTemplate<String, CompanyAdminRegisterEvent> kafkaTemplate;

	public void produceSendCompanyAdminRegisterEmail(CompanyAdminRegisterEvent companyAdminRegisterEvent) {
		kafkaTemplate.send(TOPIC_COMPANY_ADMIN_REGISTER, companyAdminRegisterEvent);
	}
}
