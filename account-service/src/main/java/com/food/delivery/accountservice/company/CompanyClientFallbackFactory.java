package com.food.delivery.accountservice.company;

import lombok.extern.slf4j.Slf4j;
import reactivefeign.FallbackFactory;
import reactor.core.publisher.Mono;

@Slf4j
class CompanyClientFallbackFactory implements FallbackFactory<CompanyClient> {
	@Override
	public CompanyClient apply(Throwable throwable) {
		return companyId -> {
			log.warn("Company with id: {} not found", companyId);
			return Mono.empty();
		};
	}
}
