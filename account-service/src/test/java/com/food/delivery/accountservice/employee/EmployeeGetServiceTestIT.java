package com.food.delivery.accountservice.employee;

import com.food.delivery.accountservice.employee.domain.Employee;
import com.food.delivery.accountservice.support.AccountServiceIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.food.delivery.accountservice.employee.EmployeeProvider.getEmployee;
import static com.food.delivery.accountservice.utils.RandomStringProvider.randomString;
import static org.assertj.core.api.Assertions.assertThat;


@AccountServiceIntegrationTest
class EmployeeGetServiceTestIT {

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private EmployeeGetService employeeGetService;

	@Test
	@DisplayName("Should return all employees")
	void shouldReturnAllEmployees() {
		//arrange
		final var employee1 = createEmployee();
		final var employee2 = createEmployee();
		final var employee3 = createEmployee();

		//act
		final var result = employeeGetService.findAll().collectList().block();

		//assert
		assertThat(result).usingRecursiveFieldByFieldElementComparator()
				.containsExactlyInAnyOrder(employee1, employee2, employee3);
	}

	@Test
	@DisplayName("Should return empty list when there is no employee")
	void shouldReturnEmptyList() {
		//arrange && act
		final var result = employeeGetService.findAll().collectList().block();

		//assert
		assertThat(result).isEmpty();
	}

	@Test
	@DisplayName("Should get employee by email")
	void shouldGetEmployeeByEmail() {
		//arrange
		final var employee = createEmployee();

		//act
		final var result = employeeGetService.findByEmail(employee.getEmail()).block();

		//assert
		assertThat(result).isEqualToComparingFieldByField(employee);
	}

	@Test
	@DisplayName("Should get empty when no user found by email")
	void shouldReturnEmpty() {
		//arrange && act
		final var result = employeeGetService.findByEmail(randomString()).blockOptional();

		//assert
		assertThat(result).isEmpty();
	}


	private Employee createEmployee() {
		return employeeRepository.save(getEmployee()).block();
	}
}
