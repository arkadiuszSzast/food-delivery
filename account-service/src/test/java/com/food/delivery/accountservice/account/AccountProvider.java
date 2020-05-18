package com.food.delivery.accountservice.account;

import com.food.delivery.accountservice.account.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountProvider {

	@Autowired
	private AccountRepository accountRepository;

	public Account createAccountAndSave(String email) {
		final var account = Account.builder()
				.email(email)
				.build();
		return accountRepository.save(account).block();
	}

	public Account createAccountAndSave(String firstName, String lastName, String email, String oktaId) {
		final var account = Account.builder()
				.firstName(firstName)
				.lastName(lastName)
				.email(email)
				.oktaId(oktaId)
				.build();
		return accountRepository.save(account).block();
	}
}
