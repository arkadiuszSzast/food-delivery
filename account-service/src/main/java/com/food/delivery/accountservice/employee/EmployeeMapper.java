package com.food.delivery.accountservice.employee;

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
		return modelMapper.map(employee, EmployeeRest.EmployeeRestBuilder.class)
				.build();
	}
}
