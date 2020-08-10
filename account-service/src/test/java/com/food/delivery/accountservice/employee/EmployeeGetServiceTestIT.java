package com.food.delivery.accountservice.employee;

import com.food.delivery.accountservice.account.AccountRest;
import com.food.delivery.accountservice.company.CompanyClient;
import com.food.delivery.accountservice.company.CompanyRest;
import com.food.delivery.accountservice.employee.domain.Employee;
import com.food.delivery.accountservice.support.AccountServiceIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;

import static com.food.delivery.accountservice.employee.EmployeeProvider.getEmployee;
import static com.food.delivery.accountservice.utils.RandomStringProvider.randomString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@AccountServiceIntegrationTest
class EmployeeGetServiceTestIT {

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private EmployeeGetService employeeGetService;
	@MockBean(name = "com.food.delivery.accountservice.company.CompanyClient")
	private CompanyClient companyClient;

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
		final var companyName = "companyName";
		final var employeeRest = EmployeeRest.builder()
				.accountRest(new AccountRest(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmail()))
				.companyId(employee.getCompanyId())
				.companyName(companyName)
				.build();

		when(companyClient.getCompany(employee.getCompanyId())).thenReturn(Mono.just(new CompanyRest(employee.getCompanyId(), companyName)));

		//act
		final var result = employeeGetService.findByEmail(employee.getEmail()).block();

		//assert
		assertThat(result).usingRecursiveComparison().isEqualTo(employeeRest);
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
