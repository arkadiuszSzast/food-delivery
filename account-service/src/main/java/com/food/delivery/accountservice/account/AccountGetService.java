package com.food.delivery.accountservice.account;

import com.food.delivery.accountservice.account.domain.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AccountGetService {

	private final AccountRepository accountRepository;

	public Flux<Account> findAll() {
		return accountRepository.findAll();
	}

	public Mono<Account> findByEmail(String email) {
		return accountRepository.findByEmail(email);
	}
}
