package com.food.delivery.accountservice.employee.domain;

import com.food.delivery.accountservice.account.AccountActivation;
import com.food.delivery.accountservice.account.AccountRest;
import com.food.delivery.accountservice.company.CompanyClient;
import com.food.delivery.accountservice.company.CompanyRest;
import com.food.delivery.accountservice.employee.EmployeeProvider;
import com.food.delivery.accountservice.employee.EmployeeRepository;
import com.food.delivery.accountservice.employee.events.EmployeeActivateProducer;
import com.food.delivery.accountservice.jwt.JwtServiceClient;
import com.food.delivery.accountservice.support.AccountServiceIntegrationTest;
import com.food.delivery.accountservice.user.okta.OktaAccountRest;
import com.food.delivery.accountservice.user.okta.OktaAdapterAccountClient;
import com.food.delivery.accountservice.utils.JwtProvider;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.food.delivery.accountservice.employee.EmployeeProvider.getEmployee;
import static com.food.delivery.accountservice.utils.RandomStringProvider.randomString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@AccountServiceIntegrationTest
class EmployeeControllerTestIT {

	@MockBean
	private CompanyClient companyClient;
	@MockBean
	private OktaAdapterAccountClient oktaAdapterAccountClient;
	@MockBean
	private EmployeeActivateProducer employeeActivateProducer;
	@MockBean
	private JwtServiceClient jwtServiceClient;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private EmployeeController employeeController;

	@Test
	@DisplayName("Should create employee")
	@WithMockUser(authorities = "COMPANY_ADMIN")
	void shouldCreateEmployee() {
		//arrange
		final var companyId = randomString();
		final var accountRest = new AccountRest(randomString(), randomString(), randomString());
		final var oktaAccountRest = new OktaAccountRest(accountRest, UUID.randomUUID().toString());
		final var companyAdmin = employeeRepository.save(EmployeeProvider.getEmployee(companyId, randomString())).block();
		final var employee = getEmployee(accountRest, companyId);
		final var jwtAuthenticationToken = new JwtAuthenticationToken(JwtProvider.getJwtWithSubject(companyAdmin.getEmail()));
		final var companyRest = new CompanyRest(companyId, randomString());
		when(companyClient.getCompany(companyId)).thenReturn(Mono.just(companyRest));
		when(oktaAdapterAccountClient.createEmployee(accountRest)).thenReturn(Mono.just(oktaAccountRest));

		//act
		final var result = employeeController.create(accountRest, jwtAuthenticationToken).block();

		//assert
		assertThat(result).isEqualToIgnoringGivenFields(employee, "id", "oktaId");
	}

	@Test
	@DisplayName("Should not create employee when no authority")
	@WithMockUser(authorities = "USER")
	void shouldNotCreateEmployeeWhenNoAuthority() {
		//arrange
		final var companyId = randomString();
		final var accountRest = new AccountRest(randomString(), randomString(), randomString());
		final var companyAdmin = employeeRepository.save(EmployeeProvider.getEmployee(companyId, randomString())).block();
		final var employee = getEmployee(accountRest, companyId);
		final var jwtAuthenticationToken = new JwtAuthenticationToken(JwtProvider.getJwtWithSubject(companyAdmin.getEmail()));

		//act && assert
		assertThrows(AccessDeniedException.class,
				() -> employeeController.create(accountRest, jwtAuthenticationToken).block());
		verify(employeeActivateProducer, never()).produceSendEmployeeActivateEmail(any());
	}

	@Test
	@DisplayName("Should not create employee when email taken")
	@WithMockUser(authorities = "COMPANY_ADMIN")
	void shouldNotCreateEmployeeWhenEmailTaken() {
		//arrange
		final var companyId = randomString();
		final var accountRest = new AccountRest(randomString(), randomString(), randomString());
		final var oktaAccountRest = new OktaAccountRest(accountRest, UUID.randomUUID().toString());
		final var companyAdmin = employeeRepository.save(EmployeeProvider.getEmployee(companyId, randomString())).block();
		final var employee = getEmployee(accountRest, companyId);
		employeeRepository.save(employee).block();
		final var jwtAuthenticationToken = new JwtAuthenticationToken(JwtProvider.getJwtWithSubject(companyAdmin.getEmail()));
		final var companyRest = new CompanyRest(companyId, randomString());
		when(companyClient.getCompany(companyId)).thenReturn(Mono.just(companyRest));
		when(oktaAdapterAccountClient.createEmployee(accountRest)).thenReturn(Mono.just(oktaAccountRest));

		//act && assert
		assertThrows(DuplicateKeyException.class,
				() -> employeeController.create(accountRest, jwtAuthenticationToken).block());
		verify(employeeActivateProducer, never()).produceSendEmployeeActivateEmail(any());
	}

	@Test
	@DisplayName("Should return information about me")
	void shouldReturnInfoAboutMe() {
		//arrange
		final var companyId = randomString();
		final var employee = employeeRepository.save(EmployeeProvider.getEmployee(companyId, randomString())).block();
		final var jwtAuthenticationToken = new JwtAuthenticationToken(JwtProvider.getJwtWithSubject(employee.getEmail()));

		//act
		final var result = employeeController.findMe(jwtAuthenticationToken).block();

		//assert
		assertThat(result).isEqualToComparingFieldByField(employee);
	}

	@Test
	@DisplayName("Should activate employee")
	void shouldActivateEmployee() {
		//arrange
		final var token = randomString();
		final var userId = randomString();
		final var activationUrl = randomString();
		final var accountActivation = new AccountActivation(token, activationUrl);
		when(jwtServiceClient.validateEmployeeActivateToken(token)).thenReturn(Mono.just(userId));
		when(oktaAdapterAccountClient.activateAccount(userId)).thenReturn(Mono.just(accountActivation));

		//act
		final var result = employeeController.activateAccount(token).block();

		//assert
		assertThat(result).isEqualToComparingFieldByField(accountActivation);
	}

	@Test
	@DisplayName("Should not activate employee when token is invalid")
	void shouldNotActivateEmployee() {
		//arrange
		final var token = randomString();
		when(jwtServiceClient.validateEmployeeActivateToken(token)).thenThrow(FeignException.class);

		//act && assert
		assertThrows(FeignException.class,
				() -> employeeController.activateAccount(token).block());
	}

}
