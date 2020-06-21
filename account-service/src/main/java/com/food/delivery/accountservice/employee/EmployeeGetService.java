package com.food.delivery.accountservice.employee;

import com.food.delivery.accountservice.employee.domain.Employee;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EmployeeGetService {

	private final EmployeeRepository employeeRepository;

	public Flux<Employee> findAll() {
		return employeeRepository.findAll();
	}

	public Mono<Employee> findByEmail(String email) {
		return employeeRepository.findByEmail(email);
	}
}
