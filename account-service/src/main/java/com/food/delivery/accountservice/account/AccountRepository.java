package com.food.delivery.accountservice.account;

import com.food.delivery.accountservice.account.domain.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

interface AccountRepository extends ReactiveMongoRepository<Account, String> {

	Mono<Account> findByEmail(String email);
}
