package com.food.delivery.menuservice.company

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactivefeign.FallbackFactory
import reactor.core.publisher.Mono

internal class CompanyClientFallbackFactory : FallbackFactory<CompanyClient> {

    val logger: Logger = LoggerFactory.getLogger(CompanyClientFallbackFactory::class.java)

    override fun apply(throwable: Throwable): CompanyClient {
        return object : CompanyClient {
            override fun getCompany(companyId: String): Mono<CompanyRest> {
                logger.error("No company found with given companyId: $companyId")
                return Mono.empty<CompanyRest>()
            }
        }
    }
}
