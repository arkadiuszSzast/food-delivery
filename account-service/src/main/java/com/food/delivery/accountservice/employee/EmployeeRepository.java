package com.food.delivery.accountservice.employee;

import com.food.delivery.accountservice.employee.domain.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String> {
	Mono<Employee> findByEmail(String email);
}
