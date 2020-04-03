package com.food.delivery.accountservice;

import com.food.delivery.accountservice.config.SecurityConfiguration;
import com.food.delivery.accountservice.support.AccountServiceIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@AccountServiceIntegrationTest
class AccountServiceApplicationTests {

	@Autowired
	private SecurityConfiguration securityConfiguration;

	@Test
	@DisplayName("Should load context")
	void contextLoads() {
		assertThat(securityConfiguration).isNotNull();
	}

}
