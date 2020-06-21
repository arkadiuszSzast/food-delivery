package com.food.delivery.accountservice.user;

import com.food.delivery.accountservice.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserProvider {

	@Autowired
	private UserRepository userRepository;

	public User createAccountAndSave(String email) {
		final var account = User.builder()
				.email(email)
				.build();
		return userRepository.save(account).block();
	}

	public User createAccountAndSave(String firstName, String lastName, String email, String oktaId) {
		final var account = User.builder()
				.firstName(firstName)
				.lastName(lastName)
				.email(email)
				.oktaId(oktaId)
				.build();
		return userRepository.save(account).block();
	}
}
