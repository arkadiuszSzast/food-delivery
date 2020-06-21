package com.food.delivery.accountservice.employee;

import com.food.delivery.accountservice.account.AccountRest;
import com.food.delivery.accountservice.employee.domain.Employee;
import com.food.delivery.accountservice.user.okta.OktaAccountRest;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeMapper {

	private final ModelMapper modelMapper;

	public Employee toDomain(OktaAccountRest oktaAccountRest) {
		return modelMapper.map(oktaAccountRest.getAccountRest(), Employee.EmployeeBuilder.class)
				.oktaId(oktaAccountRest.getOktaId())
				.build();
	}

	public Employee toDomain(OktaAccountRest oktaAccountRest, String companyId) {
		return modelMapper.map(oktaAccountRest.getAccountRest(), Employee.EmployeeBuilder.class)
				.oktaId(oktaAccountRest.getOktaId())
				.companyId(companyId)
				.build();
	}

	public EmployeeRest toRest(Employee employee) {
		final var accountRest = modelMapper.map(employee, AccountRest.AccountRestBuilder.class)
				.build();
		return EmployeeRest.builder()
				.accountRest(accountRest)
				.companyId(employee.getCompanyId())
				.build();
	}
}
