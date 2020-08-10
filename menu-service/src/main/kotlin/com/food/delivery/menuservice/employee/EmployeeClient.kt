package com.food.delivery.menuservice.employee

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import reactivefeign.spring.config.ReactiveFeignClient
import reactor.core.publisher.Mono

@ReactiveFeignClient(value = "account-service")
interface EmployeeClient {

    @GetMapping("/account/employee")
    fun getEmployee(@RequestParam email: String): Mono<EmployeeRest>
}
