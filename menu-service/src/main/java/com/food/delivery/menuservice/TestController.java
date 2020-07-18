package com.food.delivery.menuservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestController {

	@GetMapping("/menu/test")
	public Mono<String> test() {
		return Mono.just("HELLO FROM MENU SERVICE");
	}
}
