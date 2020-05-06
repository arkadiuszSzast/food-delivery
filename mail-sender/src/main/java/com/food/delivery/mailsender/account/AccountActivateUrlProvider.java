package com.food.delivery.mailsender.account;

import com.food.delivery.mailsender.utils.properties.UrlsProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@AllArgsConstructor
public class AccountActivateUrlProvider {

	private final UrlsProperties urlsProperties;


	public String getAccountActivateUrl(String token) {
		return UriComponentsBuilder
				.fromHttpUrl(urlsProperties.getAccountActivateUrl())
				.queryParam("token", token)
				.toUriString();
	}
}
