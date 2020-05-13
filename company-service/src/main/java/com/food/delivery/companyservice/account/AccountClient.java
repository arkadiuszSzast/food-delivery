package com.food.delivery.companyservice.account;

import org.springframework.web.bind.annotation.GetMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient("account-service")
public interface AccountClient {

	@GetMapping("/account/me")
	Mono<Account> findAccount();
}
