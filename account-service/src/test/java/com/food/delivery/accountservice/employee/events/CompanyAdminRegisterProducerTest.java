package com.food.delivery.accountservice.employee.events;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static com.food.delivery.accountservice.utils.RandomStringProvider.randomString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CompanyAdminRegisterProducerTest {

	private static final String TOPIC_COMPANY_ADMIN_REGISTER = "company-admin-register";

	@Mock
	private KafkaTemplate<String, CompanyAdminRegisterEvent> kafkaTemplate;
	@InjectMocks
	private CompanyAdminRegisterProducer companyAdminRegisterProducer;

	@Test
	@DisplayName("Should produce company admin activate event")
	void shouldProduceCompanyAdminActivate() {
		//arrange
		final var companyAdminRegisterEvent = new CompanyAdminRegisterEvent(randomString());

		//act
		companyAdminRegisterProducer.produceSendCompanyAdminRegisterEmail(companyAdminRegisterEvent);

		//assert
		verify(kafkaTemplate, times(1)).send(TOPIC_COMPANY_ADMIN_REGISTER, companyAdminRegisterEvent);
	}
}
