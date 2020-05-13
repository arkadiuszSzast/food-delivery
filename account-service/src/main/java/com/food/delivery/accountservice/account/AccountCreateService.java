package com.food.delivery.accountservice.account;

import com.food.delivery.accountservice.account.domain.Account;
import com.food.delivery.accountservice.account.events.AccountActivateEvent;
import com.food.delivery.accountservice.account.events.AccountActivateProducer;
import com.food.delivery.accountservice.account.model.AccountRest;
import com.food.delivery.accountservice.account.okta.OktaAdapterAccountClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AccountCreateService {

	private final AccountMapper accountMapper;
	private final AccountRepository accountRepository;
	private final OktaAdapterAccountClient oktaAdapterAccountClient;
	private final AccountActivateProducer accountActivateProducer;

	public Mono<Account> create(AccountRest accountRest) {
		return oktaAdapterAccountClient.createAccount(accountRest)
				.map(accountMapper::toDomain)
				.flatMap(accountRepository::save)
				.doOnNext(this::produceSendUserActivateEmail);
	}

	private void produceSendUserActivateEmail(Account account) {
		final var accountActivateEvent = new AccountActivateEvent(account.getId(), account.getEmail(),
				account.getFirstName(), account.getOktaId());
		accountActivateProducer.produceSendUserActivateEmail(accountActivateEvent);
	}
}
