package com.food.delivery.menuservice.menu

import com.food.delivery.menuservice.company.CompanyGetService
import com.food.delivery.menuservice.menu.domain.toRest
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class MenuCreateService(private val menuRepository: MenuRepository, private val companyGetService: CompanyGetService) {

	fun create(menuRest: MenuRest, principal: JwtAuthenticationToken): Mono<MenuRest> {
		val email = principal.name
		return companyGetService.getCompany(email)
				.map { menuRest.withCompanyId(it.id) }
				.flatMap { menuRepository.save(it.toDomain()) }
				.map { it.toRest() }
				.switchIfEmpty(Mono.error(CannotCreateMenuException()))
	}
}
