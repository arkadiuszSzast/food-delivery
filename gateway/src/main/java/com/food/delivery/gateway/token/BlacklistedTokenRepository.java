package com.food.delivery.gateway.token;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface BlacklistedTokenRepository extends ReactiveMongoRepository<BlacklistedToken, String> {
	Mono<BlacklistedToken> findByToken(String token);
}
