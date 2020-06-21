package com.food.delivery.oktaadapter.account.domain;

import com.food.delivery.oktaadapter.group.GroupGetService;
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
class AccountControllerTestIT {

	@Autowired
	private AccountController accountController;
	@Autowired
	private Client client;
	@Autowired
	private GroupGetService groupGetService;
	@Autowired
	private OktaUserDeleteService oktaUserDeleteService;

	@BeforeEach
	void init() {
		oktaUserDeleteService.deleteTestUser();
	}

	@Test
	@DisplayName("Should create user account")
	void shouldCreateUserAccount() {
		//arrange
		final var accountRest = AccountRestFactory.getAccountRest();
		final var userGroup = groupGetService.getUserGroup();

		//act
		final var account = accountController.createUser(accountRest).block();

		//assert
		final var user = client.getUser(account.getOktaId());
		assertAll(
				() -> assertThat(account.getOktaId()).isNotNull(),
				() -> assertThat(account.getAccountRest().getFirstName()).isEqualTo(accountRest.getFirstName()),
				() -> assertThat(account.getAccountRest().getLastName()).isEqualTo(accountRest.getLastName()),
				() -> assertThat(account.getAccountRest().getEmail()).isEqualTo(accountRest.getEmail()),
				() -> assertThat(user.listGroups()).hasSize(2),
				() -> assertThat(user.listGroups()).usingElementComparatorOnFields("id").contains(userGroup)
		);
	}

	@Test
	@DisplayName("Should activate account")
	void shouldActivateAccount() {
		//arrange
		final var accountRest = AccountRestFactory.getAccountRest();
		final var account = accountController.createUser(accountRest).block();

		//act
		final var activationToken = accountController.activateAccount(account.getOktaId()).block();

		//assert
		assertAll(
				() -> assertThat(activationToken.getActivationToken()).isNotNull(),
				() -> assertThat(activationToken.getActivationUrl()).isNotNull()
		);
	}

	@Test
	@DisplayName("Should create employee account")
	void shouldCreateEmployeeAccount() {
		//arrange
		final var accountRest = AccountRestFactory.getAccountRest();
		final var companyEmployeeGroup = groupGetService.getCompanyEmployeeGroup();

		//act
		final var account = accountController.createEmployee(accountRest).block();

		//assert
		final var user = client.getUser(account.getOktaId());
		assertAll(
				() -> assertThat(account.getOktaId()).isNotNull(),
				() -> assertThat(account.getAccountRest().getFirstName()).isEqualTo(accountRest.getFirstName()),
				() -> assertThat(account.getAccountRest().getLastName()).isEqualTo(accountRest.getLastName()),
				() -> assertThat(account.getAccountRest().getEmail()).isEqualTo(accountRest.getEmail()),
				() -> assertThat(user.listGroups()).hasSize(2),
				() -> assertThat(user.listGroups()).usingElementComparatorOnFields("id").contains(companyEmployeeGroup)
		);
	}

	@Test
	@DisplayName("Should create company admin account")
	void shouldCreateCompanyAdminAccount() {
		//arrange
		final var accountRest = AccountRestFactory.getAccountRest();
		final var companyEmployeeGroup = groupGetService.getCompanyEmployeeGroup();
		final var companyAdminGroup = groupGetService.getCompanyAdminGroup();

		//act
		final var account = accountController.createCompanyAdmin(accountRest).block();

		//assert
		final var user = client.getUser(account.getOktaId());
		assertAll(
				() -> assertThat(account.getOktaId()).isNotNull(),
				() -> assertThat(account.getAccountRest().getFirstName()).isEqualTo(accountRest.getFirstName()),
				() -> assertThat(account.getAccountRest().getLastName()).isEqualTo(accountRest.getLastName()),
				() -> assertThat(account.getAccountRest().getEmail()).isEqualTo(accountRest.getEmail()),
				() -> assertThat(user.listGroups()).hasSize(3),
				() -> assertThat(user.listGroups()).usingElementComparatorOnFields("id").contains(companyAdminGroup, companyEmployeeGroup)
		);
	}

}
