package com.food.delivery.accountservice.account;

import com.food.delivery.accountservice.account.domain.AccountController;
import com.food.delivery.accountservice.account.model.AccountActivation;
import com.food.delivery.accountservice.account.model.AccountRest;
import com.food.delivery.accountservice.account.okta.OktaAccountRest;
import com.food.delivery.accountservice.account.okta.OktaAdapterAccountClient;
import com.food.delivery.accountservice.jwt.JwtServiceClient;
import com.food.delivery.accountservice.support.AccountServiceIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Mono;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@AccountServiceIntegrationTest
class AccountControllerTestIT {

	@Autowired
	private AccountController accountController;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private AccountProvider accountProvider;
	@MockBean
	private OktaAdapterAccountClient oktaAdapterAccountClient;
	@MockBean
	private JwtServiceClient jwtServiceClient;

	@Test
	@DisplayName("Should create new account")
	void shouldCreateNewAccount() {
		//arrange
		final var firstName = "Joe";
		final var lastName = "Doe";
		final var email = "joe@mail.com";
		final var oktaId = "oktaId";
		final var accountRest = new AccountRest(firstName, lastName, email);
		final var oktaAccountRest = new OktaAccountRest(accountRest, oktaId);
		when(oktaAdapterAccountClient.createAccount(accountRest)).thenReturn(Mono.just(oktaAccountRest));

		//act
		accountController.create(accountRest).block();

		//assert
		assertThat(accountRepository.findByEmail(email).blockOptional()).isPresent();
	}

	@Test
	@DisplayName("Should not create account when already exists")
	void shouldNotCreateAccountWhenAlreadyExists() {
		//arrange
		final var firstName = "Joe";
		final var lastName = "Doe";
		final var email = "joe@mail.com";
		final var oktaId = "oktaId";
		final var accountRest = new AccountRest(firstName, lastName, email);
		final var oktaAccountRest = new OktaAccountRest(accountRest, oktaId);
		when(oktaAdapterAccountClient.createAccount(accountRest)).thenReturn(Mono.just(oktaAccountRest));

		//act && assert
		accountController.create(accountRest).block();
		assertThrows(DuplicateKeyException.class, () -> accountController.create(accountRest).block());
	}

	@Test
	@DisplayName("Should return all accounts")
	void shouldReturnAllAccounts() {
		//arrange
		final var account_1 = accountProvider
				.createAccountAndSave("Joe", "Doe", "joe@mail.com", "oktaId");
		final var account_2 = accountProvider
				.createAccountAndSave("Moe", "Foo", "moe@mail.com", "oktaId2");
		final var account_3 = accountProvider
				.createAccountAndSave("Too", "Boe", "too@mail.com", "oktaId3");

		//act
		final var result = accountController.findAll().collectList().block();

		//arrange
		assertAll(
				() -> assertThat(result).hasSize(3),
				() -> assertThat(result)
						.usingFieldByFieldElementComparator()
						.containsExactlyInAnyOrder(account_1, account_2, account_3)
		);
	}

	@Test
	@DisplayName("Should return my account")
	void shouldReturnMyAccount() {
		//arrange
		final Jwt jwt = getEmptyJwt();
		final var email = "joe@mail.com";
		final var jwtAuthenticationToken = new JwtAuthenticationToken(jwt, Set.of(), email);
		accountProvider.createAccountAndSave(email);

		//act
		final var result = accountController.findMe(jwtAuthenticationToken).blockOptional();

		//assert
		assertThat(result).isPresent();
	}

	private Jwt getEmptyJwt() {
		return Jwt.withTokenValue("empty")
				.header("", "")
				.claim("", "")
				.build();
	}

	@Test
	@DisplayName("Should activate account")
	void shouldActivateAccount() {
		//arrange
		final var token = "token";
		final var subject = "subject";
		final var activationToken = "activationToken";
		final var activationUrl = "activationUrl";
		final var accountActivation = new AccountActivation(activationToken, activationUrl);
		when(jwtServiceClient.validateAccountActivateToken(token)).thenReturn(Mono.just(subject));
		when(oktaAdapterAccountClient.activateAccount(subject)).thenReturn(Mono.just(accountActivation));

		//act
		final var result = accountController.activateAccount(token).block();

		//assert
		assertAll(
				() -> assertThat(result).isNotNull(),
				() -> assertThat(result.getActivationToken()).isEqualTo(activationToken),
				() -> assertThat(result.getActivationUrl()).isEqualTo(activationUrl)
		);
	}

}
