package com.food.delivery.oktaadapter;

import com.food.delivery.oktaadapter.support.OktaAdapterIntegrationTest;
import com.okta.sdk.client.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@OktaAdapterIntegrationTest
class OktaAdapterApplicationTests {

	@Autowired
	private Client client;

	@Test
	void contextLoads() {
		assertThat(client).isNotNull();
	}

}
