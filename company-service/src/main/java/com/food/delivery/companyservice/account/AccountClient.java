package com.food.delivery.companyservice.account;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(value = "account-service", fallbackFactory = AccountClientFallbackFactory.class)
public interface AccountClient {

	@GetMapping("/account/user/me")
	Mono<Account> findUserMe();

	@GetMapping("/account/employee/me")
	Mono<EmployeeRest> findEmployeeMe();

	@GetMapping("/account/employee")
	Mono<EmployeeRest> findEmployeeByEmail(@RequestParam String email);

	@PatchMapping("/account/employee/company-admin/{companyId}")
	Mono<EmployeeRest> assignCompany(@PathVariable String companyId);
}
