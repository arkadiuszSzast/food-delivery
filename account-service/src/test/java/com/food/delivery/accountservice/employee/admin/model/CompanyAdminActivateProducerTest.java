package com.food.delivery.accountservice.employee.admin.model;

import com.food.delivery.accountservice.user.events.UserActivateEvent;
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
class CompanyAdminActivateProducerTest {

	private static final String ACTIVATE_COMPANY_ADMIN_TOPIC = "activate-company-admin";

	@Mock
	private KafkaTemplate<String, UserActivateEvent> kafkaTemplate;
	@InjectMocks
	private CompanyAdminActivateProducer companyAdminActivateProducer;

	@Test
	@DisplayName("Should produce company admin activate event")
	void shouldProduceCompanyAdminActivate() {
		//arrange
		final var userActivateEvent = new UserActivateEvent(randomString(), randomString(),
				randomString(), randomString());

		//act
		companyAdminActivateProducer.produceSendCompanyAdminActivateEmail(userActivateEvent);

		//assert
		verify(kafkaTemplate, times(1)).send(ACTIVATE_COMPANY_ADMIN_TOPIC, userActivateEvent);
	}

}
