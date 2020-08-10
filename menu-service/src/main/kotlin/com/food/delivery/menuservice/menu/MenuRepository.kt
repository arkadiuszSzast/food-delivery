package com.food.delivery.menuservice.menu

import com.food.delivery.menuservice.menu.domain.Menu
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface MenuRepository : ReactiveMongoRepository<Menu, String>
