package com.food.delivery.menuservice.menu

import com.food.delivery.menuservice.menu.domain.Menu
import com.food.delivery.menuservice.menu.domain.MenuItem

data class MenuRest(val id: String?, val companyId: String?, val items: List<MenuItem>)

fun MenuRest.toDomain(): Menu = companyId?.let { Menu(id, companyId, items) } ?: throw IllegalArgumentException()

fun MenuRest.withCompanyId(companyId: String) = MenuRest(id, companyId, items)

