package com.food.delivery.accountservice.user;

import com.food.delivery.accountservice.account.AccountRest;
import com.food.delivery.accountservice.user.domain.User;
import com.food.delivery.accountservice.user.events.UserActivateEvent;
import com.food.delivery.accountservice.user.events.UserActivateProducer;
import com.food.delivery.accountservice.user.okta.OktaAdapterAccountClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserCreateService {

	private final UserMapper userMapper;
	private final UserRepository userRepository;
	private final OktaAdapterAccountClient oktaAdapterAccountClient;
	private final UserActivateProducer userActivateProducer;

	public Mono<User> create(AccountRest accountRest) {
		return oktaAdapterAccountClient.createUser(accountRest)
				.map(userMapper::toDomain)
				.flatMap(userRepository::save)
				.doOnNext(this::produceSendUserActivateEmail);
	}

	private void produceSendUserActivateEmail(User user) {
		final var accountActivateEvent = new UserActivateEvent(user.getId(), user.getEmail(),
				user.getFirstName(), user.getOktaId());
		userActivateProducer.produceSendUserActivateEmail(accountActivateEvent);
	}
}
