package com.food.delivery.accountservice.employee.events;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeActivateProducer {

	private static final String TOPIC_ACTIVATE_EMPLOYEE = "activate-employee";

	private final KafkaTemplate<String, EmployeeActivateEvent> kafkaTemplate;

	public void produceSendEmployeeActivateEmail(EmployeeActivateEvent employeeActivateEvent) {
		kafkaTemplate.send(TOPIC_ACTIVATE_EMPLOYEE, employeeActivateEvent);
	}

}
