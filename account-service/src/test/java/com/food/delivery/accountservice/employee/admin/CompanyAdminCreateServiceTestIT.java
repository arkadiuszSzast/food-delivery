package com.food.delivery.accountservice.employee.admin;

import com.food.delivery.accountservice.account.AccountRest;
import com.food.delivery.accountservice.employee.EmployeeRepository;
import com.food.delivery.accountservice.employee.admin.model.CompanyAdminActivateProducer;
import com.food.delivery.accountservice.employee.events.CompanyAdminRegisterProducer;
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
import static org.mockito.Mockito.*;

@AccountServiceIntegrationTest
class CompanyAdminCreateServiceTestIT {

	@MockBean
	private CompanyAdminActivateProducer companyAdminActivateProducer;
	@MockBean
	private CompanyAdminRegisterProducer companyAdminRegisterProducer;
	@MockBean(name = "com.food.delivery.accountservice.user.okta.OktaAdapterAccountClient")
	private OktaAdapterAccountClient oktaAdapterAccountClient;
	@Autowired
	private CompanyAdminCreateService companyAdminCreateService;
	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	@DisplayName("Should init company admin register process")
	void shouldInitCompanyAdminRegister() {
		//arrange
		final var email = randomString();

		//act
		companyAdminCreateService.initRegister(email).block();

		//assert
		verify(companyAdminRegisterProducer, times(1)).produceSendCompanyAdminRegisterEmail(any());
	}

	@Test
	@DisplayName("Should register company admin")
	void shouldRegisterCompanyAdmin() {
		//arrange
		final var accountRest = new AccountRest(randomString(), randomString(), randomString(), randomString());
		final var oktaAccountRest = new OktaAccountRest(accountRest, randomString());
		final var companyAdmin = getEmployee(accountRest);
		when(oktaAdapterAccountClient.createCompanyAdmin(accountRest)).thenReturn(Mono.just(oktaAccountRest));

		//act
		final var result = companyAdminCreateService.register(accountRest).block();

		//assert
		assertThat(result).isEqualToIgnoringGivenFields(companyAdmin, "id", "oktaId");
		verify(companyAdminActivateProducer, times(1)).produceSendCompanyAdminActivateEmail(any());
	}

	@Test
	@DisplayName("Should not create company admin when email is taken")
	void shouldNotCreateEmployee() {
		//arrange
		final var employeeEmail = randomString();
		final var accountRest = new AccountRest(randomString(), randomString(), randomString(), employeeEmail);
		final var oktaAccountRest = new OktaAccountRest(accountRest, UUID.randomUUID().toString());
		employeeRepository.save(getEmployee(accountRest)).block();
		when(oktaAdapterAccountClient.createCompanyAdmin(accountRest)).thenReturn(Mono.just(oktaAccountRest));


		//act && assert
		assertThrows(DuplicateKeyException.class,
				() -> companyAdminCreateService.register(accountRest).block());
	}

	@Test
	@DisplayName("Should assign company")
	void shouldAssignCompany() {
		//arrange
		final var employeeEmail = randomString();
		final var companyId = randomString();
		final var accountRest = new AccountRest(randomString(), randomString(), randomString(), employeeEmail);
		employeeRepository.save(getEmployee(accountRest)).block();
		final var jwtAuthenticationToken = new JwtAuthenticationToken(JwtProvider.getJwtWithSubject(accountRest.getEmail()));

		//act
		companyAdminCreateService.assignCompany(jwtAuthenticationToken, companyId).block();

		//assert
		final var employeeWithCompanyId = employeeRepository.findByEmail(employeeEmail).block();
		assertThat(employeeWithCompanyId.getCompanyId()).isEqualTo(companyId);
	}

}
