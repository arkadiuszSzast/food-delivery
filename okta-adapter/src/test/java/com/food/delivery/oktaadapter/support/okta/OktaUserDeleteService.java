package com.food.delivery.oktaadapter.support.okta;

import com.okta.sdk.client.Client;
import com.okta.sdk.resource.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OktaUserDeleteService {

	public static final String TEST_USER_MAIL = "joe@mail.com";
	@Autowired
	private Client client;

	public void deleteTestUser() {
		client.listUsers().stream()
				.filter(user -> user.getProfile().getEmail().equals(TEST_USER_MAIL))
				.forEach(this::deleteUserFromOkta);
	}

	private void deleteUserFromOkta(User user) {
		user.deactivate();
		user.delete();
	}
}
