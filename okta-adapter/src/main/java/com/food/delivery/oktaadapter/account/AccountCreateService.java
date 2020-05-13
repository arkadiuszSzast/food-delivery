package com.food.delivery.oktaadapter.account;

import com.food.delivery.oktaadapter.group.GroupGetService;
import com.okta.sdk.client.Client;
import com.okta.sdk.resource.user.UserBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AccountCreateService {

	private final Client oktaClient;
	private final GroupGetService groupGetService;
	private final AccountMapper accountMapper;

	public Mono<OktaAccountRest> createAccount(AccountRest accountRest) {
		final var userGroup = groupGetService.getUserGroup();

		final var user = UserBuilder.instance()
				.setEmail(accountRest.getEmail())
				.setFirstName(accountRest.getFirstName())
				.setLastName(accountRest.getLastName())
				.setActive(false)
				.addGroup(userGroup.getId())
				.buildAndCreate(oktaClient);

		return Mono.just(user)
				.map(accountMapper::mapToOktaAccountRest);
	}
}
