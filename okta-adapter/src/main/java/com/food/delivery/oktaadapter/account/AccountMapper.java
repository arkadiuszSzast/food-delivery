package com.food.delivery.oktaadapter.account;

import com.okta.sdk.resource.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountMapper {

	public OktaAccountRest mapToOktaAccountRest(User user) {
		final var profile = user.getProfile();
		final var accountRest = new AccountRest(profile.getFirstName(), profile.getLastName(), profile.getEmail());
		return new OktaAccountRest(accountRest, user.getId());
	}
}
