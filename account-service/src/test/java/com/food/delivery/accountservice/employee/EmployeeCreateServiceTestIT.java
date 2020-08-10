package com.food.delivery.accountservice.employee;

import com.food.delivery.accountservice.account.AccountRest;
import com.food.delivery.accountservice.company.CompanyClient;
import com.food.delivery.accountservice.company.CompanyRest;
import com.food.delivery.accountservice.support.AccountServiceIntegrationTest;
import com.food.delivery.accountservice.user.okta.OktaAccountRest;
import com.food.delivery.accountservice.user.okta.OktaAdapterAccountClient;
import com.food.delivery.accountservice.utils.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.food.delivery.accountservice.employee.EmployeeProvider.getEmployee;
import static com.food.delivery.accountservice.utils.RandomStringProvider.randomString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@AccountServiceIntegrationTest
class EmployeeCreateServiceTestIT {

	@MockBean(name = "com.food.delivery.accountservice.company.CompanyClient")
	private CompanyClient companyClient;
	@MockBean(name = "com.food.delivery.accountservice.user.okta.OktaAdapterAccountClient")
	private OktaAdapterAccountClient oktaAdapterAccountClient;
	@Autowired
	private EmployeeCreateService employeeCreateService;
	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	@DisplayName("Should create employee")
	void shouldCreateEmployee() {
		//arrange
		final var companyId = randomString();
		final var accountRest = new AccountRest(randomString(), randomString(), randomString(), randomString());
		final var oktaAccountRest = new OktaAccountRest(accountRest, UUID.randomUUID().toString());
		final var companyAdmin = employeeRepository.save(EmployeeProvider.getEmployee(companyId, randomString())).block();
		final var employee = getEmployee(accountRest, companyId);
		final var jwtAuthenticationToken = new JwtAuthenticationToken(JwtProvider.getJwtWithSubject(companyAdmin.getEmail()));
		final var companyRest = new CompanyRest(companyId, randomString());
		final var employeeRest = EmployeeRest.builder()
				.accountRest(new AccountRest(accountRest.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmail()))
				.companyId(employee.getCompanyId())
				.build();
		when(companyClient.getCompany(companyId)).thenReturn(Mono.just(companyRest));
		when(oktaAdapterAccountClient.createEmployee(accountRest)).thenReturn(Mono.just(oktaAccountRest));

		//act
		final var result = employeeCreateService.create(accountRest, jwtAuthenticationToken).block();

		//assert
		assertThat(result).usingRecursiveComparison().isEqualTo(employeeRest);
	}

	@Test
	@DisplayName("Should not create employee when email is taken")
	void shouldNotCreateEmployee() {
		//arrange
		final var companyId = randomString();
		final var employeeEmail = randomString();
		final var accountRest = new AccountRest(randomString(), randomString(), randomString(), employeeEmail);
		final var oktaAccountRest = new OktaAccountRest(accountRest, UUID.randomUUID().toString());
		final var companyAdmin = employeeRepository.save(EmployeeProvider.getEmployee(companyId, employeeEmail)).block();
		final var jwtAuthenticationToken = new JwtAuthenticationToken(JwtProvider.getJwtWithSubject(companyAdmin.getEmail()));
		final var companyRest = new CompanyRest(companyId, randomString());
		when(companyClient.getCompany(companyId)).thenReturn(Mono.just(companyRest));
		when(oktaAdapterAccountClient.createEmployee(accountRest)).thenReturn(Mono.just(oktaAccountRest));

		//act && assert
		assertThrows(DuplicateKeyException.class,
				() -> employeeCreateService.create(accountRest, jwtAuthenticationToken).block());
	}

}
