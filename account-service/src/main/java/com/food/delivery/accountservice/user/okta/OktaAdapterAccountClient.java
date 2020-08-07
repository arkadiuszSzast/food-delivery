package com.food.delivery.accountservice.user.okta;

import com.food.delivery.accountservice.account.AccountActivation;
import com.food.delivery.accountservice.account.AccountRest;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient("okta-adapter")
public interface OktaAdapterAccountClient {

	@PostMapping("/okta/account/user")
	Mono<OktaAccountRest> createUser(@RequestBody AccountRest accountRest);

	@PostMapping("/okta/account/employee")
	Mono<OktaAccountRest> createEmployee(@RequestBody AccountRest accountRest);

	@PostMapping("/okta/account/company-admin")
	Mono<OktaAccountRest> createCompanyAdmin(@RequestBody AccountRest accountRest);

	@PatchMapping("/okta/account/activate")
	Mono<AccountActivation> activateAccount(@RequestParam String userId);
}
