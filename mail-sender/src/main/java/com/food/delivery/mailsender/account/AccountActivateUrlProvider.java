package com.food.delivery.mailsender.account;

import com.food.delivery.mailsender.utils.properties.UrlsProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@AllArgsConstructor
public class AccountActivateUrlProvider {

	private final UrlsProperties urlsProperties;

	public String getUserActivateUrl(String token) {
		final var baseUrl = urlsProperties.getUserActivateUrl();
		return getAccountActivateUrl(baseUrl, token);
	}

	public String getEmployeeActivateUrl(String token) {
		final var baseUrl = urlsProperties.getEmployeeActivateUrl();
		return getAccountActivateUrl(baseUrl, token);
	}

	public String getCompanyAdminActivateUrl(String token) {
		final var baseUrl = urlsProperties.getCompanyAdminActivateUrl();
		return getAccountActivateUrl(baseUrl, token);
	}

	public String getCompanyAdminRegisterUrl(String token) {
		final var baseUrl = urlsProperties.getCompanyAdminRegisterUrl();
		return getAccountActivateUrl(baseUrl, token);
	}

	private String getAccountActivateUrl(String baseUrl, String token) {
		return UriComponentsBuilder
				.fromHttpUrl(baseUrl)
				.queryParam("token", token)
				.toUriString();
	}
}
