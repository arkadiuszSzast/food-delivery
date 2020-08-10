package com.food.delivery.accountservice.employee;

import com.food.delivery.accountservice.company.CompanyClient;
import com.food.delivery.accountservice.employee.domain.Employee;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EmployeeGetService {

	private final EmployeeMapper employeeMapper;
	private final CompanyClient companyClient;
	private final EmployeeRepository employeeRepository;

	public Flux<Employee> findAll() {
		return employeeRepository.findAll();
	}

	public Mono<EmployeeRest> findByEmail(String email) {
		return employeeRepository.findByEmail(email)
				.flatMap(employee -> companyClient.getCompany(employee.getCompanyId())
						.map(companyRest -> employeeMapper.toRest(employee, companyRest.getName())));
	}
}
