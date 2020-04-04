package com.food.delivery.accountservice.account;

import reactivefeign.spring.config.ReactiveFeignClient;

@ReactiveFeignClient("okta-adapter")
interface OktaAdapterAccountClient {
}
