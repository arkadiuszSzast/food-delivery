package com.food.delivery.accountservice.account;

import com.food.delivery.accountservice.account.model.AccountActivation;
import com.food.delivery.accountservice.account.okta.OktaAdapterAccountClient;
import com.food.delivery.accountservice.jwt.JwtServiceClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountActivateServiceTest {

	@Mock
	private JwtServiceClient jwtServiceClient;
	@Mock
	private OktaAdapterAccountClient oktaAdapterAccountClient;
	@InjectMocks
	private AccountActivateService accountActivateService;

	@Test
	@DisplayName("Should activate account")
	void shouldActivateAccount() {
		final var token = "token";
		final var subject = "subject";
		final var activationToken = "activationToken";
		final var activationUrl = "activationUrl";
		final var accountActivation = new AccountActivation(activationToken, activationUrl);
		when(jwtServiceClient.validateAccountActivateToken(token)).thenReturn(Mono.just(subject));
		when(oktaAdapterAccountClient.activateAccount(subject)).thenReturn(Mono.just(accountActivation));

		final var result = accountActivateService.activateAccount(token).block();

		//assert
		assertAll(
				() -> assertThat(result).isNotNull(),
				() -> assertThat(result.getActivationToken()).isEqualTo(activationToken),
				() -> assertThat(result.getActivationUrl()).isEqualTo(activationUrl)
		);
	}

}
