package com.food.delivery.oktaadapter.support.okta;

import com.okta.sdk.resource.user.User;
import org.springframework.stereotype.Component;

@Component
public class OktaUserDeleteService {

	public void deleteUserFromOkta(User user) {
		user.deactivate();
		user.delete();
	}
}
