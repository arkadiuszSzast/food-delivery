package com.food.delivery.oktaadapter.account.domain;

import com.food.delivery.oktaadapter.group.GroupGetService;
import com.food.delivery.oktaadapter.support.OktaAdapterIntegrationTest;
import com.food.delivery.oktaadapter.support.account.AccountRestFactory;
import com.food.delivery.oktaadapter.support.okta.OktaUserDeleteService;
import com.okta.sdk.client.Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@OktaAdapterIntegrationTest
class AccountControllerTestIT {

	@Autowired
	private AccountController accountController;
	@Autowired
	private Client client;
	@Autowired
	private GroupGetService groupGetService;
	@Autowired
	private OktaUserDeleteService oktaUserDeleteService;

	@Test
	@DisplayName("Should create account")
	void shouldCreateAccount() {
		//arrange
		final var accountRest = AccountRestFactory.getAccountRest();
		final var userGroup = groupGetService.getUserGroup();

		//act
		final var account = accountController.createAccount(accountRest).block();

		//assert
		final var user = client.getUser(account.getOktaId());
		assertAll(
				() -> assertThat(account.getOktaId()).isNotNull(),
				() -> assertThat(account.getAccountRest().getFirstName()).isEqualTo(accountRest.getFirstName()),
				() -> assertThat(account.getAccountRest().getLastName()).isEqualTo(accountRest.getLastName()),
				() -> assertThat(account.getAccountRest().getEmail()).isEqualTo(accountRest.getEmail()),
				() -> assertThat(user.getCreated()).isEqualToIgnoringSeconds(new Date()),
				() -> assertThat(user.listGroups()).hasSize(2),
				() -> assertThat(user.listGroups()).contains(userGroup)
		);

		//clean
		oktaUserDeleteService.deleteUserFromOkta(user);
	}

	@Test
	@DisplayName("Should activate account")
	void shouldActivateAccount() {
		//arrange
		final var accountRest = AccountRestFactory.getAccountRest();
		final var account = accountController.createAccount(accountRest).block();
		final var user = client.getUser(account.getOktaId());

		//act
		final var activationToken = accountController.activateAccount(account.getOktaId()).block();

		//assert
		assertAll(
				() -> assertThat(activationToken.getActivationToken()).isNotNull(),
				() -> assertThat(activationToken.getActivationUrl()).isNotNull()
		);

		//clean
		oktaUserDeleteService.deleteUserFromOkta(user);
	}

}
