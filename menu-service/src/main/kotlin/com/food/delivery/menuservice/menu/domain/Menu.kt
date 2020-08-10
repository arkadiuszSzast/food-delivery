package com.food.delivery.menuservice.menu.domain

import com.food.delivery.menuservice.menu.MenuRest
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Menu(@Id val id: String?, val companyId: String, val items: List<MenuItem>)

fun Menu.toRest() = MenuRest(id, companyId, items)
