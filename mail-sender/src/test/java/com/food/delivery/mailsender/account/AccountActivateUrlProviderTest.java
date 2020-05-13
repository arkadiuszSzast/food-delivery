package com.food.delivery.mailsender.account;

import com.food.delivery.mailsender.utils.properties.UrlsProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountActivateUrlProviderTest {

	@Mock
	private UrlsProperties urlsProperties;
	@InjectMocks
	private AccountActivateUrlProvider accountActivateUrlProvider;

	@Test
	@DisplayName("Should return user activation url with token")
	void shouldReturnActivationUrl() {
		//arrange
		final var token = "25aIGS2R";
		final var accountActivateUrl = "http://localhost:8060/account/activate";
		when(urlsProperties.getAccountActivateUrl()).thenReturn(accountActivateUrl);

		//act
		final var result = accountActivateUrlProvider.getAccountActivateUrl(token);

		//assert
		assertThat(result).isEqualTo(accountActivateUrl + "?token=" + token);
	}

}
