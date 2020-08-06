package com.food.delivery.menuservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@EnableEurekaClient
@SpringBootApplication
class MenuServiceApplication

fun main(args: Array<String>) {
	runApplication<MenuServiceApplication>(*args)
}
