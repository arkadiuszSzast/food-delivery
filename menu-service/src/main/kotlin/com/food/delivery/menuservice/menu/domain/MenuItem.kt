package com.food.delivery.menuservice.menu.domain

import java.math.BigDecimal

data class MenuItem(val name: String, val description: String, val quantity: Int,
                    val price: BigDecimal, val additives: List<String> = emptyList())
