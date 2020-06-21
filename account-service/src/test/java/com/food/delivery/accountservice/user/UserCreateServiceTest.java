package com.food.delivery.accountservice.user;

import com.food.delivery.accountservice.account.AccountRest;
import com.food.delivery.accountservice.user.domain.User;
import com.food.delivery.accountservice.user.events.UserActivateEvent;
import com.food.delivery.accountservice.user.events.UserActivateProducer;
import com.food.delivery.accountservice.user.okta.OktaAccountRest;
import com.food.delivery.accountservice.user.okta.OktaAdapterAccountClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserCreateServiceTest {

	@Mock
	private UserMapper userMapper;
	@Mock
	private UserRepository userRepository;
	@Mock
	private OktaAdapterAccountClient oktaAdapterAccountClient;
	@Mock
	private UserActivateProducer userActivateProducer;
	@InjectMocks
	private UserCreateService userCreateService;

	@Test
	@DisplayName("Should create account")
	void shouldCreateAccount() {
		//arrange
		final var id = "id";
		final var firstName = "Joe";
		final var lastName = "Doe";
		final var email = "joe@mail.com";
		final var oktaId = "oktaId";
		final var accountRest = new AccountRest(firstName, lastName, email);
		final var oktaAccountRest = new OktaAccountRest(accountRest, oktaId);
		final var account = new User(id, firstName, lastName, email, oktaId);
		when(oktaAdapterAccountClient.createUser(accountRest)).thenReturn(Mono.just(oktaAccountRest));
		when(userMapper.toDomain(oktaAccountRest)).thenReturn(account);
		when(userRepository.save(account)).thenReturn(Mono.just(account));

		//act
		final var result = userCreateService.create(accountRest).block();

		//assert
		assertAll(
				() -> assertThat(result).isNotNull(),
				() -> assertThat(result).isEqualToComparingFieldByField(account),
				() -> verify(userActivateProducer, only()).produceSendUserActivateEmail(any(UserActivateEvent.class))
		);
	}

}
