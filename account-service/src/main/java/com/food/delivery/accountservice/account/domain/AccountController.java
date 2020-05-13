package com.food.delivery.accountservice.account.domain;

import com.food.delivery.accountservice.account.AccountActivateService;
import com.food.delivery.accountservice.account.AccountCreateService;
import com.food.delivery.accountservice.account.AccountGetService;
import com.food.delivery.accountservice.account.model.AccountActivation;
import com.food.delivery.accountservice.account.model.AccountRest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/account")
public class AccountController {

	private final AccountGetService accountGetService;
	private final AccountCreateService accountCreateService;
	private final AccountActivateService accountActivateService;

	@GetMapping
	public Flux<Account> findAll() {
		return accountGetService.findAll();
	}

	@GetMapping("/me")
	public Mono<Account> findAll(@AuthenticationPrincipal JwtAuthenticationToken principal) {
		return accountGetService.findByEmail(principal.getName());
	}

	@PostMapping
	public Mono<Account> create(@RequestBody AccountRest accountRest) {
		return accountCreateService.create(accountRest);
	}

	@PatchMapping("/activate")
	public Mono<AccountActivation> activateAccount(@RequestParam String token) {
		return accountActivateService.activateAccount(token);
	}

}
