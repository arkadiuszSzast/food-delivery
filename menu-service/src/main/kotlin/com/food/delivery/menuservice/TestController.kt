package com.food.delivery.menuservice

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class TestController {

	@GetMapping("/menu/test")
	fun test(): Mono<String> {
		return Mono.just("HELLO FROM MENU SERVICE - KOTLIN")
	}
}
