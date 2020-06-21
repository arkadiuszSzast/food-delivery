package com.food.delivery.oktaadapter.group;

import com.okta.sdk.client.Client;
import com.okta.sdk.resource.group.Group;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GroupGetService {

	private static final String GROUP_USER = "USER";
	private static final String GROUP_COMPANY_EMPLOYEE = "COMPANY_EMPLOYEE";
	private static final String GROUP_COMPANY_ADMIN = "COMPANY_ADMIN";

	private final Client oktaClient;

	public Group getUserGroup() {
		return getGroupByName(GROUP_USER);
	}

	public Group getCompanyEmployeeGroup() {
		return getGroupByName(GROUP_COMPANY_EMPLOYEE);
	}

	public Group getCompanyAdminGroup() {
		return getGroupByName(GROUP_COMPANY_ADMIN);
	}

	private Group getGroupByName(String groupName) {
		return oktaClient.listGroups(groupName, null, null).single();
	}
}
