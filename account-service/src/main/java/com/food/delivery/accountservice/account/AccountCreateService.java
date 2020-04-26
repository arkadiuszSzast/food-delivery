package com.food.delivery.accountservice.account;

import com.food.delivery.accountservice.account.domain.Account;
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

	public Mono<Account> create(AccountRest accountRest) {
		return oktaAdapterAccountClient.createAccount(accountRest)
				.map(accountMapper::toDomain)
				.flatMap(accountRepository::save);
	}
}
