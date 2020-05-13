package com.food.delivery.jwtservice;

import com.food.delivery.jwtservice.jwt.JwtGenerateService;
import com.food.delivery.jwtservice.support.JwtServiceIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@JwtServiceIntegrationTest
class JwtServiceApplicationTests {

	@Autowired
	private JwtGenerateService jwtGenerateService;

	@Test
	@DisplayName("Should load context")
	void contextLoads() {
		assertThat(jwtGenerateService).isNotNull();
	}

}
