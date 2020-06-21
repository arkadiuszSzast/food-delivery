package com.food.delivery.accountservice.employee;

import com.food.delivery.accountservice.account.AccountRest;
import com.food.delivery.accountservice.company.CompanyGetService;
import com.food.delivery.accountservice.company.CompanyRest;
import com.food.delivery.accountservice.employee.events.EmployeeActivateProducer;
import com.food.delivery.accountservice.user.okta.OktaAccountRest;
import com.food.delivery.accountservice.user.okta.OktaAdapterAccountClient;
import com.food.delivery.accountservice.utils.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.food.delivery.accountservice.employee.EmployeeProvider.getEmployee;
import static com.food.delivery.accountservice.utils.RandomStringProvider.randomString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeCreateServiceTest {

	@Mock
	private OktaAdapterAccountClient oktaAdapterAccountClient;
	@Mock
	private EmployeeRepository employeeRepository;
	@Mock
	private EmployeeActivateProducer employeeActivateProducer;
	@Mock
	private EmployeeMapper employeeMapper;
	@Mock
	private CompanyGetService companyGetService;
	@InjectMocks
	private EmployeeCreateService employeeCreateService;

	@Test
	@DisplayName("Should create employee")
	void shouldCreateEmployee() {
		//arrange
		final var companyId = randomString();
		final var companyAdminEmail = randomString();
		final var accountRest = new AccountRest(randomString(), randomString(), randomString());
		final var oktaAccountRest = new OktaAccountRest(accountRest, UUID.randomUUID().toString());
		final var employee = getEmployee(accountRest, companyId);
		final var jwtAuthenticationToken = new JwtAuthenticationToken(JwtProvider.getJwtWithSubject(companyAdminEmail));
		final var companyRest = new CompanyRest(companyId, randomString());
		when(companyGetService.getCompany(companyAdminEmail)).thenReturn(Mono.just(companyRest));
		when(employeeMapper.toDomain(oktaAccountRest, companyId)).thenReturn(employee);
		when(employeeRepository.save(employee)).thenReturn(Mono.just(employee));
		when(oktaAdapterAccountClient.createEmployee(accountRest)).thenReturn(Mono.just(oktaAccountRest));

		//act
		final var result = employeeCreateService.create(accountRest, jwtAuthenticationToken).block();

		//assert
		assertThat(result).isEqualToIgnoringGivenFields(employee, "id", "oktaId");
	}

	@Test
	@DisplayName("Should create employee")
	void shouldNotCreateEmployee() {
		//arrange
		final var companyId = randomString();
		final var companyAdminEmail = randomString();
		final var accountRest = new AccountRest(randomString(), randomString(), randomString());
		final var oktaAccountRest = new OktaAccountRest(accountRest, UUID.randomUUID().toString());
		final var employee = getEmployee(accountRest, companyId);
		final var jwtAuthenticationToken = new JwtAuthenticationToken(JwtProvider.getJwtWithSubject(companyAdminEmail));
		final var companyRest = new CompanyRest(companyId, randomString());
		when(companyGetService.getCompany(companyAdminEmail)).thenReturn(Mono.just(companyRest));
		when(employeeMapper.toDomain(oktaAccountRest, companyId)).thenReturn(employee);
		when(employeeRepository.save(employee)).thenThrow(DuplicateKeyException.class);
		when(oktaAdapterAccountClient.createEmployee(accountRest)).thenReturn(Mono.just(oktaAccountRest));

		//act && assert
		assertThrows(DuplicateKeyException.class,
				() -> employeeCreateService.create(accountRest, jwtAuthenticationToken).block());
	}


}
