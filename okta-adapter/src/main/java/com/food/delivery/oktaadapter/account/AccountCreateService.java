package com.food.delivery.oktaadapter.account;

import com.food.delivery.oktaadapter.group.GroupGetService;
import com.okta.sdk.client.Client;
import com.okta.sdk.resource.group.Group;
import com.okta.sdk.resource.user.UserBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountCreateService {

	private final Client oktaClient;
	private final GroupGetService groupGetService;
	private final AccountMapper accountMapper;

	public Mono<OktaAccountRest> createEmployee(AccountRest accountRest) {
		final var companyEmployeeGroup = groupGetService.getCompanyEmployeeGroup();

		return createAccount(accountRest, List.of(companyEmployeeGroup));
	}

	public Mono<OktaAccountRest> createCompanyAdmin(AccountRest accountRest) {
		final var companyEmployeeGroup = groupGetService.getCompanyEmployeeGroup();
		final var companyAdminGroup = groupGetService.getCompanyAdminGroup();

		return createAccount(accountRest, List.of(companyEmployeeGroup, companyAdminGroup));
	}

	public Mono<OktaAccountRest> createUser(AccountRest accountRest) {
		final var userGroup = groupGetService.getUserGroup();

		return createAccount(accountRest, List.of(userGroup));
	}

	private Mono<OktaAccountRest> createAccount(AccountRest accountRest, List<Group> groups) {

		final Set<String> groupsIds = getGroupIds(groups);

		final var user = UserBuilder.instance()
				.setEmail(accountRest.getEmail())
				.setFirstName(accountRest.getFirstName())
				.setLastName(accountRest.getLastName())
				.setActive(false)
				.setGroups(groupsIds)
				.buildAndCreate(oktaClient);

		return Mono.just(user)
				.map(accountMapper::mapToOktaAccountRest);
	}

	private Set<String> getGroupIds(List<Group> groups) {
		return groups.stream()
				.map(Group::getId)
				.collect(Collectors.toUnmodifiableSet());
	}
}
