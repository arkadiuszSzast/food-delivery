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
	void shouldReturnUserActivationUrl() {
		//arrange
		final var token = "25aIGS2R";
		final var accountActivateUrl = "http://localhost:8060/account/activate";
		when(urlsProperties.getUserActivateUrl()).thenReturn(accountActivateUrl);

		//act
		final var result = accountActivateUrlProvider.getUserActivateUrl(token);

		//assert
		assertThat(result).isEqualTo(accountActivateUrl + "?token=" + token);
	}

	@Test
	@DisplayName("Should return employee activation url with token")
	void shouldReturnEmployeeActivationUrl() {
		//arrange
		final var token = "25aIGS2R";
		final var accountActivateUrl = "http://localhost:8060/account/activate";
		when(urlsProperties.getEmployeeActivateUrl()).thenReturn(accountActivateUrl);

		//act
		final var result = accountActivateUrlProvider.getEmployeeActivateUrl(token);

		//assert
		assertThat(result).isEqualTo(accountActivateUrl + "?token=" + token);
	}

	@Test
	@DisplayName("Should return company admin activation url with token")
	void shouldReturnCompanyAdminActivationUrl() {
		//arrange
		final var token = "25aIGS2R";
		final var accountActivateUrl = "http://localhost:8060/account/activate";
		when(urlsProperties.getCompanyAdminActivateUrl()).thenReturn(accountActivateUrl);

		//act
		final var result = accountActivateUrlProvider.getCompanyAdminActivateUrl(token);

		//assert
		assertThat(result).isEqualTo(accountActivateUrl + "?token=" + token);
	}

	@Test
	@DisplayName("Should return company admin register url with token")
	void shouldReturnCompanyAdminRegisterUrl() {
		//arrange
		final var token = "25aIGS2R";
		final var accountActivateUrl = "http://localhost:8060/account/activate";
		when(urlsProperties.getCompanyAdminRegisterUrl()).thenReturn(accountActivateUrl);

		//act
		final var result = accountActivateUrlProvider.getCompanyAdminRegisterUrl(token);

		//assert
		assertThat(result).isEqualTo(accountActivateUrl + "?token=" + token);
	}

}
