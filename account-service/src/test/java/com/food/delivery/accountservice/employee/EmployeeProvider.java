package com.food.delivery.accountservice.employee;

import com.food.delivery.accountservice.account.AccountRest;
import com.food.delivery.accountservice.employee.domain.Employee;

import static com.food.delivery.accountservice.utils.RandomStringProvider.randomString;

public class EmployeeProvider {

	public static Employee getEmployee() {
		return Employee.builder()
				.firstName(randomString())
				.lastName(randomString())
				.email(randomString())
				.build();
	}

	public static Employee getEmployee(AccountRest accountRest) {
		return Employee.builder()
				.firstName(accountRest.getFirstName())
				.lastName(accountRest.getLastName())
				.email(accountRest.getEmail())
				.build();
	}

	public static Employee getEmployee(AccountRest accountRest, String companyId) {
		return Employee.builder()
				.firstName(accountRest.getFirstName())
				.lastName(accountRest.getLastName())
				.email(accountRest.getEmail())
				.companyId(companyId)
				.build();
	}

	public static Employee getEmployee(String companyId, String email) {
		return Employee.builder()
				.email(email)
				.companyId(companyId)
				.build();
	}

	public static Employee getEmployee(AccountRest accountRest, String oktaId, String id) {
		return Employee.builder()
				.id(id)
				.firstName(accountRest.getFirstName())
				.lastName(accountRest.getLastName())
				.oktaId(oktaId)
				.email(accountRest.getEmail())
				.build();
	}
}
