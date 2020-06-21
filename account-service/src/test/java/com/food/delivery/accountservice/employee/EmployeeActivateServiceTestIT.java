package com.food.delivery.accountservice.employee;

import com.food.delivery.accountservice.account.AccountActivation;
import com.food.delivery.accountservice.jwt.JwtServiceClient;
import com.food.delivery.accountservice.support.AccountServiceIntegrationTest;
import com.food.delivery.accountservice.user.okta.OktaAdapterAccountClient;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;

import static com.food.delivery.accountservice.utils.RandomStringProvider.randomString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@AccountServiceIntegrationTest
class EmployeeActivateServiceTestIT {

	@MockBean
	private JwtServiceClient jwtServiceClient;
	@MockBean
	private OktaAdapterAccountClient oktaAdapterAccountClient;
	@Autowired
	private EmployeeActivateService employeeActivateService;

	@Test
	@DisplayName("Should activate employee when token is valid")
	void shouldActivateEmployee() {
		//arrange
		final var token = randomString();
		final var activationUrl = randomString();
		final var userId = randomString();
		final var accountActivation = new AccountActivation(token, activationUrl);
		when(jwtServiceClient.validateEmployeeActivateToken(token)).thenReturn(Mono.just(userId));
		when(oktaAdapterAccountClient.activateAccount(userId)).thenReturn(Mono.just(accountActivation));

		//act
		final var result = employeeActivateService.activateAccount(token).block();

		//assert
		assertThat(result).isEqualToComparingFieldByField(accountActivation);
	}

	@Test
	@DisplayName("Should not activate employee when token is invalid")
	void shouldNotActivateEmployee() {
		//arrange
		final var token = randomString();
		when(jwtServiceClient.validateEmployeeActivateToken(token)).thenThrow(FeignException.class);

		//act && assert
		assertThrows(FeignException.class,
				() -> employeeActivateService.activateAccount(token).block());
	}

}
