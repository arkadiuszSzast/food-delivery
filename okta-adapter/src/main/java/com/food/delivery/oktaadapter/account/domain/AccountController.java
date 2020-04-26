package com.food.delivery.oktaadapter.account.domain;

import com.food.delivery.oktaadapter.account.AccountCreateService;
import com.food.delivery.oktaadapter.account.AccountRest;
import com.okta.sdk.resource.user.User;
import com.okta.sdk.resource.user.UserProfile;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/account")
public class AccountController {

	private final AccountCreateService accountCreateService;

	@PostMapping
	public Mono<UserProfile> createAccount(@RequestBody AccountRest accountRest) {
		return Mono.just(accountCreateService.createAccount(accountRest))
				.map(User::getProfile);
	}
}
