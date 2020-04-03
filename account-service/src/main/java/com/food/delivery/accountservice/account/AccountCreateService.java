package com.food.delivery.accountservice.account;

import com.food.delivery.accountservice.account.domain.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Service
@AllArgsConstructor
public class AccountCreateService {

	private final AccountRepository accountRepository;

	public Mono<Account> create(Principal principal) {
		return accountRepository.save(new Account(principal.getName()));
	}
}
