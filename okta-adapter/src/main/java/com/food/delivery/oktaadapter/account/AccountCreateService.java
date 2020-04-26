package com.food.delivery.oktaadapter.account;

import com.food.delivery.oktaadapter.group.GroupGetService;
import com.okta.sdk.client.Client;
import com.okta.sdk.resource.user.User;
import com.okta.sdk.resource.user.UserBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountCreateService {

	private final Client oktaClient;
	private final GroupGetService groupGetService;

	public User createAccount(AccountRest accountRest) {
		final var userGroup = groupGetService.getUserGroup();

		return UserBuilder.instance()
				.setEmail(accountRest.getEmail())
				.setFirstName(accountRest.getFirstName())
				.setLastName(accountRest.getLastName())
				.setActive(false)
				.addGroup(userGroup.getId())
				.buildAndCreate(oktaClient);
	}
}
