package com.food.delivery.oktaadapter.account;

import com.food.delivery.oktaadapter.group.GroupGetService;
import com.food.delivery.oktaadapter.support.OktaAdapterIntegrationTest;
import com.food.delivery.oktaadapter.support.account.AccountRestFactory;
import com.food.delivery.oktaadapter.support.okta.OktaUserDeleteService;
import com.okta.sdk.client.Client;
import com.okta.sdk.resource.ResourceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@OktaAdapterIntegrationTest
class AccountCreateServiceTestIT {

	@Autowired
	private AccountCreateService accountCreateService;
	@Autowired
	private GroupGetService groupGetService;
	@Autowired
	private OktaUserDeleteService oktaUserDeleteService;
	@Autowired
	private Client client;

	@Test
	@DisplayName("Should create account")
	void shouldCreateAccount() {
		//arrange
		final var accountRest = AccountRestFactory.getAccountRest();
		final var userGroup = groupGetService.getUserGroup();

		//act
		final var account = accountCreateService.createAccount(accountRest).block();
		final var user = client.getUser(account.getOktaId());

		//assert
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
	@DisplayName("Should not create account when email already exist")
	void shouldNotCreateAccount() {
		//arrange
		final var accountRest = AccountRestFactory.getAccountRest();

		//act && assert
		final var firstAccount = accountCreateService.createAccount(accountRest).block();
		assertThrows(ResourceException.class, () -> accountCreateService.createAccount(accountRest).block());

		//clean
		final var user = client.getUser(firstAccount.getOktaId());
		oktaUserDeleteService.deleteUserFromOkta(user);
	}

}
