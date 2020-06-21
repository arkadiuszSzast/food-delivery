package com.food.delivery.accountservice.employee;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.food.delivery.accountservice.employee.EmployeeProvider.getEmployee;
import static com.food.delivery.accountservice.utils.RandomStringProvider.randomString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeGetServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;
	@InjectMocks
	private EmployeeGetService employeeGetService;

	@Test
	@DisplayName("Should return all employees")
	void shouldReturnAllEmployees() {
		//arrange
		final var employee1 = getEmployee();
		final var employee2 = getEmployee();
		final var employee3 = getEmployee();
		when(employeeRepository.findAll()).thenReturn(Flux.just(employee1, employee2, employee3));

		//act
		final var result = employeeGetService.findAll().collectList().block();

		//assert
		assertThat(result).usingRecursiveFieldByFieldElementComparator()
				.containsExactlyInAnyOrder(employee1, employee2, employee3);
	}

	@Test
	@DisplayName("Should return empty list when there is no employee")
	void shouldReturnEmptyList() {
		//arrange

		when(employeeRepository.findAll()).thenReturn(Flux.empty());
		//act
		final var result = employeeGetService.findAll().collectList().block();

		//assert
		assertThat(result).isEmpty();
	}

	@Test
	@DisplayName("Should get employee by email")
	void shouldGetEmployeeByEmail() {
		//arrange
		final var employee = getEmployee();
		when(employeeRepository.findByEmail(employee.getEmail())).thenReturn(Mono.just(employee));

		//act
		final var result = employeeGetService.findByEmail(employee.getEmail()).block();

		//assert
		assertThat(result).isEqualToComparingFieldByField(employee);
	}

	@Test
	@DisplayName("Should get empty when no user found by email")
	void shouldReturnEmpty() {
		//arrange
		final var email = randomString();
		when(employeeRepository.findByEmail(email)).thenReturn(Mono.empty());

		//act
		final var result = employeeGetService.findByEmail(email).blockOptional();

		//assert
		assertThat(result).isEmpty();
	}


}
