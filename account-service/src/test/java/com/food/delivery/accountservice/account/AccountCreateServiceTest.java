package com.food.delivery.accountservice.account;

import com.food.delivery.accountservice.account.domain.Account;
import com.food.delivery.accountservice.account.events.AccountActivateEvent;
import com.food.delivery.accountservice.account.events.AccountActivateProducer;
import com.food.delivery.accountservice.account.model.AccountRest;
import com.food.delivery.accountservice.account.okta.OktaAccountRest;
import com.food.delivery.accountservice.account.okta.OktaAdapterAccountClient;
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
class AccountCreateServiceTest {

	@Mock
	private AccountMapper accountMapper;
	@Mock
	private AccountRepository accountRepository;
	@Mock
	private OktaAdapterAccountClient oktaAdapterAccountClient;
	@Mock
	private AccountActivateProducer accountActivateProducer;
	@InjectMocks
	private AccountCreateService accountCreateService;

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
		final var account = new Account(id, firstName, lastName, email, oktaId);
		when(oktaAdapterAccountClient.createAccount(accountRest)).thenReturn(Mono.just(oktaAccountRest));
		when(accountMapper.toDomain(oktaAccountRest)).thenReturn(account);
		when(accountRepository.save(account)).thenReturn(Mono.just(account));

		//act
		final var result = accountCreateService.create(accountRest).block();

		//assert
		assertAll(
				() -> assertThat(result).isNotNull(),
				() -> assertThat(result).isEqualToComparingFieldByField(account),
				() -> verify(accountActivateProducer, only()).produceSendUserActivateEmail(any(AccountActivateEvent.class))
		);
	}

}
