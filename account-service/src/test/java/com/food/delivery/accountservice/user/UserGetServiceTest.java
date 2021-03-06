package com.food.delivery.accountservice.user;

import com.food.delivery.accountservice.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserGetServiceTest {

	@Mock
	private UserRepository userRepository;
	@InjectMocks
	private UserGetService userGetService;

	@Test
	@DisplayName("Should return account by email")
	void shouldReturnAccountByEmail() {
		//arrange
		final var id = "id";
		final var firstName = "Joe";
		final var lastName = "Doe";
		final var email = "joe@mail.com";
		final var oktaId = "oktaId";
		final var account = new User(id, firstName, lastName, email, oktaId);
		when(userRepository.findByEmail(email)).thenReturn(Mono.just(account));

		//act
		final var result = userGetService.findByEmail(email).blockOptional();

		//assert
		assertAll(
				() -> assertThat(result).isPresent(),
				() -> assertThat(result.get()).isEqualToComparingFieldByField(account)
		);
	}

	@Test
	@DisplayName("Should not found account by email")
	void shouldNotFindAccountByEmail() {
		//arrange
		final var email = "joe@mail.com";
		when(userRepository.findByEmail(email)).thenReturn(Mono.empty());

		//act
		final var result = userGetService.findByEmail(email).blockOptional();

		//assert
		assertAll(
				() -> assertThat(result).isEmpty()
		);
	}

	@Test
	@DisplayName("Should return all accounts")
	void shouldReturnAllAccounts() {
		//arrange
		final var id = "id";
		final var firstName = "Joe";
		final var lastName = "Doe";
		final var email = "joe@mail.com";
		final var secondEmail = "joe@mail.com";
		final var oktaId = "oktaId";
		final var account = new User(id, firstName, lastName, email, oktaId);
		final var secondAccount = new User(id, firstName, lastName, secondEmail, oktaId);
		when(userRepository.findAll()).thenReturn(Flux.just(account, secondAccount));

		//act
		final var result = userGetService.findAll().collectList().block();

		//assert
		assertAll(
				() -> assertThat(result).hasSize(2),
				() -> assertThat(result)
						.usingFieldByFieldElementComparator()
						.containsExactlyInAnyOrder(account, secondAccount)
		);
	}

	@Test
	@DisplayName("Should return empty flux when no account found")
	void shouldReturnEmptyFluxWhenAccountNotFound() {
		//arrange
		when(userRepository.findAll()).thenReturn(Flux.empty());

		//act
		final var result = userGetService.findAll().collectList().block();

		//assert
		assertAll(
				() -> assertThat(result).isEmpty()
		);
	}
}
