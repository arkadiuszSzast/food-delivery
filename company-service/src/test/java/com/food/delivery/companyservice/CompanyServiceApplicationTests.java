package com.food.delivery.companyservice;

import com.food.delivery.companyservice.config.SecurityConfiguration;
import com.food.delivery.companyservice.utils.CompanyServiceIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@CompanyServiceIntegrationTest
class CompanyServiceApplicationTests {

	@Autowired
	private SecurityConfiguration securityConfiguration;

	@Test
	@DisplayName("Should load context")
	void contextLoads() {
		assertThat(securityConfiguration).isNotNull();
	}

}
