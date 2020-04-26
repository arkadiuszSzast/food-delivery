package com.food.delivery.accountservice.account;

import com.food.delivery.accountservice.account.domain.Account;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountMapper {

	private final ModelMapper modelMapper;

	public Account toDomain(AccountRest accountRest) {
		return modelMapper.map(accountRest, Account.AccountBuilder.class).build();
	}

	public AccountRest toRest(Account account) {
		return modelMapper.map(account, AccountRest.AccountRestBuilder.class).build();
	}

}
