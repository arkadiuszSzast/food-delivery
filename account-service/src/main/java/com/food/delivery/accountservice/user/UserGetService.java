package com.food.delivery.accountservice.user;

import com.food.delivery.accountservice.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserGetService {

	private final UserRepository userRepository;

	public Flux<User> findAll() {
		return userRepository.findAll();
	}

	public Mono<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
}
