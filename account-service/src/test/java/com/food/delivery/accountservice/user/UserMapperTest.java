package com.food.delivery.accountservice.user;

import com.food.delivery.accountservice.account.AccountRest;
import com.food.delivery.accountservice.support.AccountServiceIntegrationTest;
import com.food.delivery.accountservice.user.domain.User;
import com.food.delivery.accountservice.user.okta.OktaAccountRest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@AccountServiceIntegrationTest
class UserMapperTest {

	private static final String EMAIL = "email@email.com";
	private static final String LAST_NAME = "Doe";
	private static final String FIRST_NAME = "Joe";
	private static final String ID = "id";
	private static final String OKTA_ID = "okta_id";

	@Autowired
	private UserMapper userMapper;

	@Test
	@DisplayName("Should map accountRest to domain object")
	void shouldMapToDomainObject() {
		//arrange
		final var accountRest = new AccountRest(ID, FIRST_NAME, LAST_NAME, EMAIL);

		//act
		final var account = userMapper.toDomain(accountRest);

		//assert
		assertAll(
				() -> assertThat(account.getEmail()).isEqualTo(EMAIL),
				() -> assertThat(account.getFirstName()).isEqualTo(FIRST_NAME),
				() -> assertThat(account.getLastName()).isEqualTo(LAST_NAME),
				() -> assertThat(account.getId()).isEqualTo(ID)
		);
	}

	@Test
	@DisplayName("Should map domain object to accountRest")
	void shouldMapToRestObject() {
		//arrange
		final var account = new User(ID, FIRST_NAME, LAST_NAME, EMAIL, OKTA_ID);

		//act
		final var accountRest = userMapper.toRest(account);

		//assert
		assertAll(
				() -> assertThat(accountRest.getEmail()).isEqualTo(EMAIL),
				() -> assertThat(accountRest.getFirstName()).isEqualTo(FIRST_NAME),
				() -> assertThat(accountRest.getLastName()).isEqualTo(LAST_NAME),
				() -> assertThat(account.getId()).isEqualTo(ID)
		);
	}

	@Test
	@DisplayName("Should map oktaAccountRest to domain object")
	void shouldMapToDomainObjectFromOktaRest() {
		//arrange
		final var accountRest = new AccountRest(ID, FIRST_NAME, LAST_NAME, EMAIL);
		final var oktaAccountRest = new OktaAccountRest(accountRest, OKTA_ID);

		//act
		final var account = userMapper.toDomain(oktaAccountRest);

		//assert
		assertAll(
				() -> assertThat(account.getEmail()).isEqualTo(EMAIL),
				() -> assertThat(account.getFirstName()).isEqualTo(FIRST_NAME),
				() -> assertThat(account.getLastName()).isEqualTo(LAST_NAME),
				() -> assertThat(account.getOktaId()).isEqualTo(OKTA_ID),
				() -> assertThat(account.getId()).isEqualTo(ID)
		);
	}

}
