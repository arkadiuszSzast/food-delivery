package com.food.delivery.oktaadapter.account;

import com.food.delivery.oktaadapter.support.OktaAdapterIntegrationTest;
import com.food.delivery.oktaadapter.support.account.AccountRestFactory;
import com.food.delivery.oktaadapter.support.okta.OktaUserDeleteService;
import com.okta.sdk.client.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@OktaAdapterIntegrationTest
class AccountActivateServiceTestIT {

	@Autowired
	private AccountActivateService accountActivateService;
	@Autowired
	private AccountCreateService accountCreateService;
	@Autowired
	private OktaUserDeleteService oktaUserDeleteService;
	@Autowired
	private Client client;

	@BeforeEach
	void init() {
		oktaUserDeleteService.deleteTestUser();
	}

	@Test
	@DisplayName("Should activate account")
	void shouldActivateAccount() {
		//arrange
		final var accountRest = AccountRestFactory.getAccountRest();
		final var account = accountCreateService.createAccount(accountRest).block();

		//act
		final var activationToken = accountActivateService.activateAccount(account.getOktaId()).block();

		//assert
		assertAll(
				() -> assertThat(activationToken.getActivationToken()).isNotNull(),
				() -> assertThat(activationToken.getActivationUrl()).isNotNull()
		);
	}

}
