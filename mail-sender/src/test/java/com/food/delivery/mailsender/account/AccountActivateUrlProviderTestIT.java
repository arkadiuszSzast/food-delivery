package com.food.delivery.mailsender.account;

import com.food.delivery.mailsender.support.MailSenderIntegrationTest;
import com.food.delivery.mailsender.utils.properties.UrlsProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@MailSenderIntegrationTest
class AccountActivateUrlProviderTestIT {

	@Autowired
	private UrlsProperties urlsProperties;
	@Autowired
	private AccountActivateUrlProvider accountActivateUrlProvider;

	@Test
	@DisplayName("Should return user activation url with token")
	void shouldReturnActivationUrl() {
		//arrange
		final var token = "25aIGS2R";

		//act
		final var result = accountActivateUrlProvider.getUserActivateUrl(token);

		//assert
		assertThat(result).isEqualTo(urlsProperties.getUserActivateUrl() + "?token=" + token);
	}

}
