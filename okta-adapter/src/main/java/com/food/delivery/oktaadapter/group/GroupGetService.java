package com.food.delivery.oktaadapter.group;

import com.okta.sdk.client.Client;
import com.okta.sdk.resource.group.Group;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GroupGetService {

	private static final String GROUP_USER = "USER";

	private final Client oktaClient;

	public Group getUserGroup() {
		return getGroupByName(GROUP_USER);
	}

	private Group getGroupByName(String groupName) {
		return oktaClient.listGroups(groupName, null, null).single();
	}
}
