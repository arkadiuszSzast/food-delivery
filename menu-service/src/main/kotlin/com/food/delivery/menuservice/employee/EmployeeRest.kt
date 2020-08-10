package com.food.delivery.menuservice.employee

import com.fasterxml.jackson.annotation.JsonProperty

data class EmployeeRest(@JsonProperty("account") val accountRest: AccountRest, val companyName: String?, val companyId: String)

