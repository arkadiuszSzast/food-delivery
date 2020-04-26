package com.food.delivery.accountservice.account.okta;

import com.food.delivery.accountservice.account.AccountRest;
import org.springframework.web.bind.annotation.PostMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient("okta-adapter")
public interface OktaAdapterAccountClient {

	@PostMapping("/account")
	Mono<AccountRest> createAccount(AccountRest accountRest);
}
