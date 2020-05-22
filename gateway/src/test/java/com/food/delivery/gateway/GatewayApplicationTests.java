package com.food.delivery.gateway;

import com.food.delivery.gateway.config.security.SecurityConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import support.GatewayIntegrationTest;

import static org.assertj.core.api.Assertions.assertThat;

@GatewayIntegrationTest
class GatewayApplicationTests {

	@Autowired
	private SecurityConfiguration securityConfiguration;

	@Test
	@DisplayName("Should load context")
	void contextLoads() {
		assertThat(securityConfiguration).isNotNull();
	}

}
