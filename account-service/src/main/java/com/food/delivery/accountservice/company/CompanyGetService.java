package com.food.delivery.accountservice.company;

import com.food.delivery.accountservice.employee.EmployeeRepository;
import com.food.delivery.accountservice.employee.domain.Employee;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CompanyGetService {

	private final CompanyClient companyClient;
	private final EmployeeRepository employeeRepository;

	public Mono<CompanyRest> getCompany(String employeeEmail) {
		return employeeRepository.findByEmail(employeeEmail)
				.map(Employee::getCompanyId)
				.flatMap(companyClient::getCompany);
	}
}
