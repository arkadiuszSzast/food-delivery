package com.food.delivery.menuservice.employee

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactivefeign.FallbackFactory
import reactor.core.publisher.Mono

internal class EmployeeClientFallbackFactory : FallbackFactory<EmployeeClient> {

    val logger: Logger = LoggerFactory.getLogger(EmployeeClientFallbackFactory::class.java)

    override fun apply(throwable: Throwable): EmployeeClient {
        return object : EmployeeClient {
            override fun getEmployee(email: String): Mono<EmployeeRest> {
                logger.error("No employee found with given email: $email")
                return Mono.empty<EmployeeRest>()
            }
        }
    }
}
