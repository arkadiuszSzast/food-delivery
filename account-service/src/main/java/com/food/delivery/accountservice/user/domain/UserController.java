package com.food.delivery.accountservice.user.domain;

import com.food.delivery.accountservice.account.AccountActivation;
import com.food.delivery.accountservice.account.AccountRest;
import com.food.delivery.accountservice.user.UserActivateService;
import com.food.delivery.accountservice.user.UserCreateService;
import com.food.delivery.accountservice.user.UserGetService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/account/user")
public class UserController {

	private final UserGetService userGetService;
	private final UserCreateService userCreateService;
	private final UserActivateService userActivateService;

	@GetMapping
	public Flux<User> findAll() {
		return userGetService.findAll();
	}

	@GetMapping("/me")
	public Mono<User> findMe(@AuthenticationPrincipal JwtAuthenticationToken principal) {
		return userGetService.findByEmail(principal.getName());
	}

	@PostMapping
	public Mono<User> create(@RequestBody AccountRest accountRest) {
		return userCreateService.create(accountRest);
	}

	@PatchMapping("/activate")
	public Mono<AccountActivation> activateAccount(@RequestParam String token) {
		return userActivateService.activateAccount(token);
	}

}
