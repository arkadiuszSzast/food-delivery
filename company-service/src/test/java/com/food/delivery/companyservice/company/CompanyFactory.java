package com.food.delivery.companyservice.company;

import com.food.delivery.companyservice.company.address.Address;
import com.food.delivery.companyservice.company.domain.Company;

import java.util.UUID;

public class CompanyFactory {

	public Company create() {
		final var id = UUID.randomUUID().toString();
		final var companyAdminId = UUID.randomUUID().toString();
		final var name = UUID.randomUUID().toString();
		final var phoneNumber = "123123123";
		final var address = new Address(UUID.randomUUID().toString(), UUID.randomUUID().toString(),
				UUID.randomUUID().toString(), UUID.randomUUID().toString());
		return new Company(id, companyAdminId, name, phoneNumber, address);
	}

}
