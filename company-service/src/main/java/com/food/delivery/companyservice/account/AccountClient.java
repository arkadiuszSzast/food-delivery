package com.food.delivery.companyservice.account;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient("account-service")
public interface AccountClient {

	@GetMapping("/account/user/me")
	Mono<Account> findUserMe();

	@GetMapping("/account/employee/me")
	Mono<Account> findEmployeeMe();

	@PatchMapping("/account/employee/company-admin/{companyId}")
	Mono<Account> assignCompany(@PathVariable String companyId);
}
