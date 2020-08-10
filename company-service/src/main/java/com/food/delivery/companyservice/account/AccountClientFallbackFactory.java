package com.food.delivery.companyservice.account;

import lombok.extern.slf4j.Slf4j;
import reactivefeign.FallbackFactory;
import reactor.core.publisher.Mono;

@Slf4j
class AccountClientFallbackFactory implements FallbackFactory<AccountClient> {
	@Override
	public AccountClient apply(Throwable throwable) {
		return new AccountClient() {
			@Override
			public Mono<Account> findUserMe() {
				return Mono.empty();
			}

			@Override
			public Mono<EmployeeRest> findEmployeeMe() {
				return Mono.empty();
			}

			@Override
			public Mono<EmployeeRest> findEmployeeByEmail(String email) {
				return Mono.empty();
			}

			@Override
			public Mono<EmployeeRest> assignCompany(String companyId) {
				log.error("Failed to assign user to company with id: {}", companyId);
				return Mono.empty();
			}
		};
	}

}
