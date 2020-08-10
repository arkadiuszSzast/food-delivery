package com.food.delivery.menuservice.menu

import com.food.delivery.menuservice.menu.domain.toRest
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class MenuGetService(private val menuRepository: MenuRepository) {

    fun findAll(): Flux<MenuRest> {
        return menuRepository.findAll()
                .map { it.toRest() }
    }
}
