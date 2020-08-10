package com.food.delivery.accountservice.employee.admin;

import com.food.delivery.accountservice.account.AccountRest;
import com.food.delivery.accountservice.employee.EmployeeMapper;
import com.food.delivery.accountservice.employee.EmployeeRepository;
import com.food.delivery.accountservice.employee.EmployeeRest;
import com.food.delivery.accountservice.employee.admin.model.CompanyAdminActivateProducer;
import com.food.delivery.accountservice.employee.events.CompanyAdminRegisterProducer;
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

import static com.food.delivery.accountservice.employee.EmployeeProvider.getEmployee;
import static com.food.delivery.accountservice.utils.RandomStringProvider.randomString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyAdminCreateServiceTest {

	@Mock
	private OktaAdapterAccountClient oktaAdapterAccountClient;
	@Mock
	private EmployeeRepository employeeRepository;
	@Mock
	private CompanyAdminActivateProducer companyAdminActivateProducer;
	@Mock
	private CompanyAdminRegisterProducer companyAdminRegisterProducer;
	@Mock
	private EmployeeMapper employeeMapper;
	@InjectMocks
	private CompanyAdminCreateService companyAdminCreateService;

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
		final var companyAdmin = getEmployee(accountRest, oktaAccountRest.getOktaId(), randomString());
		when(oktaAdapterAccountClient.createCompanyAdmin(accountRest)).thenReturn(Mono.just(oktaAccountRest));
		when(employeeRepository.save(companyAdmin)).thenReturn(Mono.just(companyAdmin));
		when(employeeMapper.toDomain(oktaAccountRest)).thenReturn(companyAdmin);

		//act
		final var result = companyAdminCreateService.register(accountRest).block();

		//assert
		assertThat(result).isEqualToComparingFieldByField(companyAdmin);
		verify(companyAdminActivateProducer, times(1)).produceSendCompanyAdminActivateEmail(any());
	}

	@Test
	@DisplayName("Should not register company admin")
	void shouldNotRegisterCompanyAdmin() {
		//arrange
		final var accountRest = new AccountRest(randomString(), randomString(), randomString(), randomString());
		final var oktaAccountRest = new OktaAccountRest(accountRest, randomString());
		final var companyAdmin = getEmployee(accountRest, oktaAccountRest.getOktaId(), randomString());
		when(oktaAdapterAccountClient.createCompanyAdmin(accountRest)).thenReturn(Mono.just(oktaAccountRest));
		when(employeeRepository.save(companyAdmin)).thenThrow(DuplicateKeyException.class);
		when(employeeMapper.toDomain(oktaAccountRest)).thenReturn(companyAdmin);

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
		final var companyName = randomString();
		final var accountRest = new AccountRest(randomString(), randomString(), randomString(), employeeEmail);
		final var employee = getEmployee(accountRest);
		final var employeeRest = new EmployeeRest(accountRest, companyName, companyId);
		final var jwtAuthenticationToken = new JwtAuthenticationToken(JwtProvider.getJwtWithSubject(accountRest.getEmail()));
		when(employeeRepository.findByEmail(jwtAuthenticationToken.getName())).thenReturn(Mono.just(employee));
		when(employeeRepository.save(employee)).thenReturn(Mono.just(employee));
		when(employeeMapper.toRest(employee)).thenReturn(employeeRest);

		//act
		final var result = companyAdminCreateService.assignCompany(jwtAuthenticationToken, companyId).block();

		//assert
		assertThat(result.getCompanyId()).isEqualTo(companyId);
	}

}
