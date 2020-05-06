package com.food.delivery.accountservice.account.okta;

import com.food.delivery.accountservice.account.AccountRest;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient("okta-adapter")
public interface OktaAdapterAccountClient {

	@PostMapping("/account")
	Mono<OktaAccountRest> createAccount(@RequestBody AccountRest accountRest);

	@PatchMapping("/account/activate")
	Mono<Object> activateAccount(@RequestParam String userId);
}
