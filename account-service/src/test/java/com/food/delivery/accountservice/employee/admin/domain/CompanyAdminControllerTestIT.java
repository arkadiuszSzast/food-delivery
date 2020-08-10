package com.food.delivery.accountservice.employee.admin.domain;

import com.food.delivery.accountservice.account.AccountActivation;
import com.food.delivery.accountservice.account.AccountRest;
import com.food.delivery.accountservice.employee.EmployeeRepository;
import com.food.delivery.accountservice.employee.admin.model.CompanyAdminActivateProducer;
import com.food.delivery.accountservice.employee.events.CompanyAdminRegisterProducer;
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

import static com.food.delivery.accountservice.employee.EmployeeProvider.getEmployee;
import static com.food.delivery.accountservice.utils.RandomStringProvider.randomString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@AccountServiceIntegrationTest
class CompanyAdminControllerTestIT {

	@MockBean
	private CompanyAdminActivateProducer companyAdminActivateProducer;
	@MockBean
	private CompanyAdminRegisterProducer companyAdminRegisterProducer;
	@MockBean(name = "com.food.delivery.accountservice.user.okta.OktaAdapterAccountClient")
	private OktaAdapterAccountClient oktaAdapterAccountClient;
	@MockBean(name = "com.food.delivery.accountservice.jwt.JwtServiceClient")
	private JwtServiceClient jwtServiceClient;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private CompanyAdminController companyAdminController;

	@Test
	@DisplayName("Should init company admin registration")
	@WithMockUser(authorities = "ADMIN")
	void shouldInitCompanyAdminRegister() {
		//arrange
		final var email = randomString();

		//act
		companyAdminController.initCompanyAdminRegister(email).block();

		//assert
		verify(companyAdminRegisterProducer, times(1)).produceSendCompanyAdminRegisterEmail(any());
	}

	@Test
	@DisplayName("Should throw exception when try to init company admin registration when no authorities")
	@WithMockUser(authorities = "USER")
	void shouldNotInitCompanyAdminRegister() {
		//arrange
		final var email = randomString();

		//act && assert
		assertThrows(AccessDeniedException.class,
				() -> companyAdminController.initCompanyAdminRegister(email).block());
	}

	@Test
	@DisplayName("Should create company admin account")
	void shouldCreateCompanyAdmin() {
		//arrange
		final var accountRest = new AccountRest(randomString(), randomString(), randomString(), randomString());
		final var token = randomString();
		final var oktaAccountRest = new OktaAccountRest(accountRest, randomString());
		final var employee = getEmployee(accountRest);
		when(jwtServiceClient.validateCompanyAdminRegisterToken(token)).thenReturn(Mono.just(token));
		when(oktaAdapterAccountClient.createCompanyAdmin(accountRest)).thenReturn(Mono.just(oktaAccountRest));

		//act
		final var result = companyAdminController.create(accountRest, token).block();

		//assert
		assertAll(
				() -> assertThat(result).isEqualToIgnoringGivenFields(employee, "id", "oktaId"),
				() -> assertThat(result.getOktaId()).isEqualTo(oktaAccountRest.getOktaId())
		);
		verify(companyAdminActivateProducer, times(1)).produceSendCompanyAdminActivateEmail(any());
	}

	@Test
	@DisplayName("Should not create company admin account when jwt is invalid")
	void shouldNotCreateCompanyAdminWhenInvalidToken() {
		//arrange
		final var accountRest = new AccountRest(randomString(), randomString(), randomString(), randomString());
		final var token = randomString();
		when(jwtServiceClient.validateCompanyAdminRegisterToken(token)).thenThrow(FeignException.class);

		//act && assert
		assertThrows(FeignException.class,
				() -> companyAdminController.create(accountRest, token).block());
		verify(companyAdminActivateProducer, never()).produceSendCompanyAdminActivateEmail(any());
	}

	@Test
	@DisplayName("Should not create company admin account when email is taken")
	void shouldNotCreateCompanyAdminWhenEmailTaken() {
		//arrange
		final var accountRest = new AccountRest(randomString(), randomString(), randomString(), randomString());
		final var token = randomString();
		final var oktaAccountRest = new OktaAccountRest(accountRest, randomString());
		final var employee = getEmployee(accountRest);
		employeeRepository.save(employee).block();
		when(jwtServiceClient.validateCompanyAdminRegisterToken(token)).thenReturn(Mono.just(token));
		when(oktaAdapterAccountClient.createCompanyAdmin(accountRest)).thenReturn(Mono.just(oktaAccountRest));

		//act && assert
		assertThrows(DuplicateKeyException.class,
				() -> companyAdminController.create(accountRest, token).block());
		verify(companyAdminActivateProducer, never()).produceSendCompanyAdminActivateEmail(any());
	}

	@Test
	@DisplayName("Should activate company admin")
	void shouldActivateCompanyAdmin() {
		//arrange
		final var token = randomString();
		final var userId = randomString();
		final var activationUrl = randomString();
		final var accountActivation = new AccountActivation(token, activationUrl);
		when(jwtServiceClient.validateCompanyAdminActivateToken(token)).thenReturn(Mono.just(userId));
		when(oktaAdapterAccountClient.activateAccount(userId)).thenReturn(Mono.just(accountActivation));

		//act
		final var result = companyAdminController.activateAccount(token).block();

		//assert
		assertThat(result).isEqualToComparingFieldByField(accountActivation);
	}

	@Test
	@DisplayName("Should not activate company admin when token is invalid")
	void shouldNotActivateCompanyAdmin() {
		//arrange
		final var token = randomString();
		when(jwtServiceClient.validateCompanyAdminActivateToken(token)).thenThrow(FeignException.class);

		//act && assert
		assertThrows(FeignException.class,
				() -> companyAdminController.activateAccount(token).block());
	}

	@Test
	@DisplayName("Should assign company")
	@WithMockUser(authorities = "COMPANY_ADMIN")
	void shouldAssignCompany() {
		//arrange
		final var employeeEmail = randomString();
		final var companyId = randomString();
		final var accountRest = new AccountRest(randomString(), randomString(), randomString(), employeeEmail);
		employeeRepository.save(getEmployee(accountRest)).block();
		final var jwtAuthenticationToken = new JwtAuthenticationToken(JwtProvider.getJwtWithSubject(accountRest.getEmail()));

		//act
		companyAdminController.assignCompany(jwtAuthenticationToken, companyId).block();

		//assert
		final var employeeWithCompanyId = employeeRepository.findByEmail(employeeEmail).block();
		assertThat(employeeWithCompanyId.getCompanyId()).isEqualTo(companyId);
	}

	@Test
	@DisplayName("Should not assign company when no authority")
	void shouldNotAssignCompany() {
		//arrange
		final var employeeEmail = randomString();
		final var companyId = randomString();
		final var accountRest = new AccountRest(randomString(), randomString(), randomString(), employeeEmail);
		final var jwtAuthenticationToken = new JwtAuthenticationToken(JwtProvider.getJwtWithSubject(accountRest.getEmail()));

		//act && assert
		assertThrows(AccessDeniedException.class,
				() -> companyAdminController.assignCompany(jwtAuthenticationToken, companyId).block());
	}

}
