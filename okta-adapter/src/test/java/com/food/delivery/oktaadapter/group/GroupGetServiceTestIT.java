package com.food.delivery.oktaadapter.group;

import com.food.delivery.oktaadapter.support.OktaAdapterIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@OktaAdapterIntegrationTest
class GroupGetServiceTestIT {

	@Autowired
	private GroupGetService groupGetService;

	@Test
	@DisplayName("Should return user group")
	void shouldReturnUserGroup() {
		//act
		final var userGroup = groupGetService.getUserGroup();

		//arrange
		assertAll(
				() -> assertThat(userGroup).isNotNull(),
				() -> assertThat(userGroup.getId()).isNotNull()
		);

	}

}
