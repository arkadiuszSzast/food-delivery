package com.food.delivery.accountservice.utils;

import java.util.UUID;

public class RandomStringProvider {

	public static String randomString() {
		return UUID.randomUUID().toString();
	}
}
