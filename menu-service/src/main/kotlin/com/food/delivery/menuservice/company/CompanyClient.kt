package com.food.delivery.menuservice.company

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import reactivefeign.spring.config.ReactiveFeignClient
import reactor.core.publisher.Mono

@ReactiveFeignClient(value = "company-service", fallbackFactory = CompanyClientFallbackFactory::class)
interface CompanyClient {

	@GetMapping("/company/{companyId}")
	fun getCompany(@PathVariable companyId: String): Mono<CompanyRest>
}
