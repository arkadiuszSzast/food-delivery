package com.food.delivery.accountservice.user;

import com.food.delivery.accountservice.account.AccountActivation;
import com.food.delivery.accountservice.account.AccountRest;
import com.food.delivery.accountservice.jwt.JwtServiceClient;
import com.food.delivery.accountservice.support.AccountServiceIntegrationTest;
import com.food.delivery.accountservice.user.domain.UserController;
import com.food.delivery.accountservice.user.okta.OktaAccountRest;
import com.food.delivery.accountservice.user.okta.OktaAdapterAccountClient;
import com.food.delivery.accountservice.utils.JwtProvider;
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
class UserControllerTestIT {

	@Autowired
	private UserController userController;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserProvider userProvider;
	@MockBean(name = "com.food.delivery.accountservice.user.okta.OktaAdapterAccountClient")
	private OktaAdapterAccountClient oktaAdapterAccountClient;
	@MockBean(name = "com.food.delivery.accountservice.jwt.JwtServiceClient")
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
		when(oktaAdapterAccountClient.createUser(accountRest)).thenReturn(Mono.just(oktaAccountRest));

		//act
		userController.create(accountRest).block();

		//assert
		assertThat(userRepository.findByEmail(email).blockOptional()).isPresent();
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
		when(oktaAdapterAccountClient.createUser(accountRest)).thenReturn(Mono.just(oktaAccountRest));

		//act && assert
		userController.create(accountRest).block();
		assertThrows(DuplicateKeyException.class, () -> userController.create(accountRest).block());
	}

	@Test
	@DisplayName("Should return all accounts")
	void shouldReturnAllAccounts() {
		//arrange
		final var account_1 = userProvider
				.createAccountAndSave("Joe", "Doe", "joe@mail.com", "oktaId");
		final var account_2 = userProvider
				.createAccountAndSave("Moe", "Foo", "moe@mail.com", "oktaId2");
		final var account_3 = userProvider
				.createAccountAndSave("Too", "Boe", "too@mail.com", "oktaId3");

		//act
		final var result = userController.findAll().collectList().block();

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
		final Jwt jwt = JwtProvider.getEmptyJwt();
		final var email = "joe@mail.com";
		final var jwtAuthenticationToken = new JwtAuthenticationToken(jwt, Set.of(), email);
		userProvider.createAccountAndSave(email);

		//act
		final var result = userController.findMe(jwtAuthenticationToken).blockOptional();

		//assert
		assertThat(result).isPresent();
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
		when(jwtServiceClient.validateUserActivateToken(token)).thenReturn(Mono.just(subject));
		when(oktaAdapterAccountClient.activateAccount(subject)).thenReturn(Mono.just(accountActivation));

		//act
		final var result = userController.activateAccount(token).block();

		//assert
		assertAll(
				() -> assertThat(result).isNotNull(),
				() -> assertThat(result.getActivationToken()).isEqualTo(activationToken),
				() -> assertThat(result.getActivationUrl()).isEqualTo(activationUrl)
		);
	}

}
