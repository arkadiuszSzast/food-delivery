package com.food.delivery.oktaadapter.account.domain;

import com.food.delivery.oktaadapter.account.AccountActivateService;
import com.food.delivery.oktaadapter.account.AccountCreateService;
import com.food.delivery.oktaadapter.account.AccountRest;
import com.food.delivery.oktaadapter.account.OktaAccountRest;
import com.okta.sdk.resource.user.UserActivationToken;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/okta/account")
public class AccountController {

	private final AccountCreateService accountCreateService;
	private final AccountActivateService accountActivateService;

	@PostMapping("/user")
	public Mono<OktaAccountRest> createUser(@RequestBody AccountRest accountRest) {
		return accountCreateService.createUser(accountRest);
	}

	@PostMapping("/employee")
	public Mono<OktaAccountRest> createEmployee(@RequestBody AccountRest accountRest) {
		return accountCreateService.createEmployee(accountRest);
	}

	@PostMapping("/company-admin")
	public Mono<OktaAccountRest> createCompanyAdmin(@RequestBody AccountRest accountRest) {
		return accountCreateService.createCompanyAdmin(accountRest);
	}

	@PatchMapping("/activate")
	public Mono<UserActivationToken> activateAccount(@RequestParam String userId) {
		return accountActivateService.activateAccount(userId);
	}
}
