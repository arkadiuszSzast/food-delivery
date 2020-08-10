package com.food.delivery.menuservice.menu.domain

import com.food.delivery.menuservice.menu.MenuCreateService
import com.food.delivery.menuservice.menu.MenuGetService
import com.food.delivery.menuservice.menu.MenuRest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/menu")
class MenuController(private val menuCreateService: MenuCreateService, private val menuGetService: MenuGetService) {

	@PostMapping
	@PreAuthorize("hasAuthority('COMPANY_EMPLOYEE')")
	fun create(@RequestBody(required = false) menuRest: MenuRest, @AuthenticationPrincipal principal: JwtAuthenticationToken): Mono<MenuRest> {
		return menuCreateService.create(menuRest, principal)
	}

	@GetMapping
	fun findAll(): Flux<MenuRest> {
		return menuGetService.findAll()
	}
}

