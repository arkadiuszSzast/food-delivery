package com.food.delivery.accountservice.employee.admin;

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
class CompanyAdminActivateServiceTestIT {

	@MockBean(name = "com.food.delivery.accountservice.jwt.JwtServiceClient")
	private JwtServiceClient jwtServiceClient;
	@MockBean(name = "com.food.delivery.accountservice.user.okta.OktaAdapterAccountClient")
	private OktaAdapterAccountClient oktaAdapterAccountClient;
	@Autowired
	private CompanyAdminActivateService companyAdminActivateService;

	@Test
	@DisplayName("Should activate company admin when token is valid")
	void shouldActivateCompanyAdmin() {
		//arrange
		final var token = randomString();
		final var activationUrl = randomString();
		final var userId = randomString();
		final var accountActivation = new AccountActivation(token, activationUrl);
		when(jwtServiceClient.validateCompanyAdminActivateToken(token)).thenReturn(Mono.just(userId));
		when(oktaAdapterAccountClient.activateAccount(userId)).thenReturn(Mono.just(accountActivation));

		//act
		final var result = companyAdminActivateService.activateAccount(token).block();

		//assert
		assertThat(result).isEqualToComparingFieldByField(accountActivation);
	}

	@Test
	@DisplayName("Should not activate company admin when token is invalid")
	void shouldNotActivateCompanyAdmin() {
		//arrange
		final var token = randomString();
		when(jwtServiceClient.validateCompanyAdminActivateToken(token)).thenThrow(FeignException.class);

		//act && assert
		assertThrows(FeignException.class,
				() -> companyAdminActivateService.activateAccount(token).block());
	}
}
