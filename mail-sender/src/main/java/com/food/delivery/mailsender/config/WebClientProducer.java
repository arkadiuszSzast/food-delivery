package com.food.delivery.mailsender.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientProducer {

	@Bean
	public WebClient webClient(ObjectMapper baseConfig) {
		ObjectMapper newMapper = baseConfig.copy();
		newMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

		ExchangeStrategies strategies = ExchangeStrategies
				.builder()
				.codecs(clientDefaultCodecsConfigurer -> {
					clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(newMapper, MediaType.APPLICATION_JSON));
					clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(newMapper, MediaType.APPLICATION_JSON));
				}).build();
		return WebClient.builder().exchangeStrategies(strategies).build();
	}
}
