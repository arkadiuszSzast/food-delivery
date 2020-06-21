package com.food.delivery.companyservice.company.domain;

import com.food.delivery.companyservice.account.Account;
import com.food.delivery.companyservice.account.AccountClient;
import com.food.delivery.companyservice.company.CompanyProvider;
import com.food.delivery.companyservice.company.model.CompanyRest;
import com.food.delivery.companyservice.support.CompanyServiceIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolationException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@CompanyServiceIntegrationTest
class CompanyControllerTestIT {

	@MockBean
	private AccountClient accountClient;
	@Autowired
	private CompanyController companyController;
	@Autowired
	private CompanyProvider companyProvider;

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
		when(accountClient.findEmployeeMe()).thenReturn(Mono.just(account));
		when(accountClient.assignCompany(any())).thenReturn(Mono.just(account));

		//act
		final var company = companyController.createCompany(companyRest).block();

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
		when(accountClient.findEmployeeMe()).thenReturn(Mono.just(account));

		//act && assert
		assertThrows(ConstraintViolationException.class,
				() -> companyController.createCompany(companyRest).block());

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
		when(accountClient.findEmployeeMe()).thenReturn(Mono.just(account));

		//act && assert
		assertThrows(AccessDeniedException.class,
				() -> companyController.createCompany(companyRest).block());
	}

}
