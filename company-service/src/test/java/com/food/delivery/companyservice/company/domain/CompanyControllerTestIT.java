package com.food.delivery.companyservice.company.domain;

import com.food.delivery.companyservice.account.Account;
import com.food.delivery.companyservice.account.AccountClient;
import com.food.delivery.companyservice.account.AccountRest;
import com.food.delivery.companyservice.account.EmployeeRest;
import com.food.delivery.companyservice.company.CompanyProvider;
import com.food.delivery.companyservice.company.CompanyRepository;
import com.food.delivery.companyservice.company.CompanyValidationException;
import com.food.delivery.companyservice.company.model.CompanyRest;
import com.food.delivery.companyservice.support.CompanyServiceIntegrationTest;
import com.food.delivery.companyservice.utils.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@CompanyServiceIntegrationTest
class CompanyControllerTestIT {

	@MockBean(name = "com.food.delivery.companyservice.account.AccountClient")
	private AccountClient accountClient;
	@Autowired
	private CompanyController companyController;
	@Autowired
	private CompanyProvider companyProvider;
	@Autowired
	private CompanyRepository companyRepository;

	@Test
	@DisplayName("Should return list of all companies")
	void shouldReturnCompanyList() {
		//arrange
		final var company1 = companyProvider.createAndSave();
		final var company2 = companyProvider.createAndSave();
		final var company3 = companyProvider.createAndSave();
		final var company4 = companyProvider.createAndSave();

		//act
		final var result = companyController.findAll().collectList().block();

		//assert
		assertAll(
				() -> assertThat(result).hasSize(4),
				() -> assertThat(result)
						.usingRecursiveFieldByFieldElementComparator()
						.containsExactlyInAnyOrder(company1, company2, company3, company4)
		);
	}

	@Test
	@DisplayName("Should return empty list when company not found")
	void shouldReturnEmptyListWhenCompanyNotFound() {
		//act
		final var result = companyController.findAll().collectList().block();

		//assert
		assertThat(result).isEmpty();
	}

	@Test
	@WithMockUser(authorities = "COMPANY_ADMIN")
	@DisplayName("Should create company")
	void shouldCreateCompany() {
		//arrange
		final var companyId = "id";
		final var companyName = "companyName";
		final var phoneNumber = "123123123";
		final var companyRest = new CompanyRest(companyId, companyName, phoneNumber);
		final var accountName = "accountName";
		final var surname = "surname";
		final var email = "name@mail.com";
		final var account = new Account(UUID.randomUUID().toString(), accountName, surname, email);
		final var employeeRest = new EmployeeRest(new AccountRest(account.getId(), accountName, surname, email), companyName, companyId);
		final var jwtAuthenticationToken = new JwtAuthenticationToken(JwtProvider.getJwtWithSubject(email));

		when(accountClient.findEmployeeByEmail(email)).thenReturn(Mono.just(employeeRest));
		when(accountClient.assignCompany(any())).thenReturn(Mono.just(employeeRest));

		//act
		final var company = companyController.createCompany(jwtAuthenticationToken, companyRest).block();

		//assert
		final var companies = companyController.findAll().collectList().block();
		assertAll(
				() -> assertThat(companies).hasSize(1),
				() -> assertThat(companies)
						.usingRecursiveFieldByFieldElementComparator()
						.containsExactlyInAnyOrder(company)
		);
	}

	@Test
	@WithMockUser(authorities = "COMPANY_ADMIN")
	@DisplayName("Should not create company when invalid phoneNumber given")
	void shouldNotCreateCompanyWhenInvalidPhoneNumber() {
		//arrange
		final var companyId = "id";
		final var companyName = "companyName";
		final var phoneNumber = "invalidPhone";
		final var companyRest = new CompanyRest(companyId, companyName, phoneNumber);
		final var accountName = "accountName";
		final var surname = "surname";
		final var email = "name@mail.com";
		final var account = new Account(UUID.randomUUID().toString(), accountName, surname, email);
		final var employeeRest = new EmployeeRest(new AccountRest(account.getId(), accountName, surname, email), companyName, companyId);
		final var jwtAuthenticationToken = new JwtAuthenticationToken(JwtProvider.getJwtWithSubject(email));
		when(accountClient.findEmployeeByEmail(email)).thenReturn(Mono.just(employeeRest));
		when(accountClient.assignCompany(companyId)).thenReturn(Mono.just(employeeRest));

		//act && assert
		assertThrows(CompanyValidationException.class,
				() -> companyController.createCompany(jwtAuthenticationToken, companyRest).blockOptional());
	}

	@Test
	@DisplayName("Should not create company without required authority")
	void shouldNotCreateCompanyWithoutPermission() {
		//arrange
		final var companyId = "id";
		final var companyName = "companyName";
		final var phoneNumber = "invalidPhone";
		final var companyRest = new CompanyRest(companyId, companyName, phoneNumber);
		final var accountName = "accountName";
		final var surname = "surname";
		final var email = "name@mail.com";
		final var account = new Account(UUID.randomUUID().toString(), accountName, surname, email);
		final var employeeRest = new EmployeeRest(new AccountRest(account.getId(), accountName, surname, email), companyName, companyId);
		final var jwtAuthenticationToken = new JwtAuthenticationToken(JwtProvider.getJwtWithSubject(email));
		when(accountClient.findEmployeeMe()).thenReturn(Mono.just(employeeRest));

		//act && assert
		assertThrows(AccessDeniedException.class,
				() -> companyController.createCompany(jwtAuthenticationToken, companyRest).block());
	}

	@Test
	@DisplayName("Should return company found by id")
	void shouldReturnCompanyFoundById() {
		//arrange
		final var company = companyProvider.createAndSave();
		final var companyRest = new CompanyRest(company.getId(), company.getName(), company.getPhoneNumber());

		//act
		final var result = companyController.findById(company.getId()).blockOptional();

		//assert
		assertAll(
				() -> assertThat(result).isPresent(),
				() -> assertThat(result.get()).usingRecursiveComparison().isEqualTo(companyRest)
		);
	}

	@Test
	@DisplayName("Should return empty when company not found by id")
	void shouldReturnEmptyWhenCompanyNotFoundById() {
		//arrange
		final var notExistingId = "notExistingId";

		//act
		final var result = companyController.findById(notExistingId).blockOptional();

		//assert
		assertAll(
				() -> assertThat(result).isEmpty()
		);
	}

}
