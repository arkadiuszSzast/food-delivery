package com.food.delivery.accountservice.account;

import com.food.delivery.accountservice.account.domain.Account;
import com.food.delivery.accountservice.account.okta.OktaAccountRest;
import com.food.delivery.accountservice.support.AccountServiceIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@AccountServiceIntegrationTest
class AccountMapperTest {

	public static final String EMAIL = "email@email.com";
	public static final String LAST_NAME = "Doe";
	public static final String FIRST_NAME = "Joe";
	public static final String ID = "id";
	public static final String OKTA_ID = "okta_id";

	@Autowired
	private AccountMapper accountMapper;

	@Test
	@DisplayName("Should map accountRest to domain object")
	void shouldMapToDomainObject() {
		//arrange
		final var accountRest = new AccountRest(FIRST_NAME, LAST_NAME, EMAIL);

		//act
		final var account = accountMapper.toDomain(accountRest);

		//assertI
		assertAll(
				() -> assertThat(account.getEmail()).isEqualTo(EMAIL),
				() -> assertThat(account.getFirstName()).isEqualTo(FIRST_NAME),
				() -> assertThat(account.getLastName()).isEqualTo(LAST_NAME),
				() -> assertThat(account.getId()).isNull()
		);
	}

	@Test
	@DisplayName("Should map domain object to accountRest")
	void shouldMapToRestObject() {
		//arrange
		final var account = new Account(ID, FIRST_NAME, LAST_NAME, EMAIL, OKTA_ID);

		//act
		final var accountRest = accountMapper.toRest(account);

		//assert
		assertAll(
				() -> assertThat(accountRest.getEmail()).isEqualTo(EMAIL),
				() -> assertThat(accountRest.getFirstName()).isEqualTo(FIRST_NAME),
				() -> assertThat(accountRest.getLastName()).isEqualTo(LAST_NAME)
		);
	}

	@Test
	@DisplayName("Should map oktaAccountRest to domain object")
	void shouldMapToDomainObjectFromOktaRest() {
		//arrange
		final var accountRest = new AccountRest(FIRST_NAME, LAST_NAME, EMAIL);
		final var oktaAccountRest = new OktaAccountRest(accountRest, OKTA_ID);

		//act
		final var account = accountMapper.toDomain(oktaAccountRest);

		//assertI
		assertAll(
				() -> assertThat(account.getEmail()).isEqualTo(EMAIL),
				() -> assertThat(account.getFirstName()).isEqualTo(FIRST_NAME),
				() -> assertThat(account.getLastName()).isEqualTo(LAST_NAME),
				() -> assertThat(account.getOktaId()).isEqualTo(OKTA_ID),
				() -> assertThat(account.getId()).isNull()
		);
	}

}
