package com.food.delivery.accountservice.user;

import com.food.delivery.accountservice.account.AccountRest;
import com.food.delivery.accountservice.user.domain.User;
import com.food.delivery.accountservice.user.okta.OktaAccountRest;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserMapper {

	private final ModelMapper modelMapper;

	public User toDomain(AccountRest accountRest) {
		return modelMapper.map(accountRest, User.UserBuilder.class).build();
	}

	public User toDomain(OktaAccountRest oktaAccountRest) {
		return modelMapper.map(oktaAccountRest.getAccountRest(), User.UserBuilder.class)
				.oktaId(oktaAccountRest.getOktaId())
				.build();
	}

	public AccountRest toRest(User user) {
		return modelMapper.map(user, AccountRest.AccountRestBuilder.class).build();
	}

}
