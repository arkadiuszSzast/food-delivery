package com.food.delivery.accountservice.account.domain;

import com.food.delivery.accountservice.account.AccountCreateService;
import com.food.delivery.accountservice.account.AccountGetService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/account")
public class AccountController {

	private final AccountGetService accountGetService;
	private final AccountCreateService accountCreateService;

	@GetMapping
	public Flux<Account> findAll() {
		return accountGetService.findAll();
	}

	@GetMapping("/me")
	public Mono<Account> findAll(@AuthenticationPrincipal JwtAuthenticationToken principal) {
		return accountGetService.findByEmail(principal.getName());
	}

	@PostMapping
	public Mono<Account> create(@AuthenticationPrincipal Principal principal) {
		return accountCreateService.create(principal);
	}

}
