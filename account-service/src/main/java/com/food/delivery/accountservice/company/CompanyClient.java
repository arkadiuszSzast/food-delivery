package com.food.delivery.accountservice.company;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient("company-service")
public interface CompanyClient {

	@GetMapping("/company/{companyId}")
	Mono<CompanyRest> getCompany(@PathVariable String companyId);

}
