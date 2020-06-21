package com.food.delivery.accountservice.user;

import com.food.delivery.accountservice.user.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

interface UserRepository extends ReactiveMongoRepository<User, String> {

	Mono<User> findByEmail(String email);
}
