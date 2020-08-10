package com.food.delivery.companyservice.company;

import com.food.delivery.companyservice.account.Account;
import com.food.delivery.companyservice.account.AccountClient;
import com.food.delivery.companyservice.account.AccountRest;
import com.food.delivery.companyservice.account.EmployeeRest;
import com.food.delivery.companyservice.company.model.CompanyRest;
import com.food.delivery.companyservice.support.CompanyServiceIntegrationTest;
import com.food.delivery.companyservice.utils.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@CompanyServiceIntegrationTest
class CompanyCreateServiceTestIT {

	@Autowired
	private CompanyCreateService companyCreateService;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private CompanyMapper companyMapper;
	@MockBean(name = "com.food.delivery.companyservice.account.AccountClient")
	private AccountClient accountClient;

	@Test
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
		final var company = companyCreateService.create(jwtAuthenticationToken, companyRest).block();

		//assert
		final var companies = companyRepository.findAll()
				.map(companyMapper::toRest)
				.collectList()
				.block();

		assertAll(
				() -> assertThat(companies).hasSize(1),
				() -> assertThat(companies)
						.usingRecursiveFieldByFieldElementComparator()
						.containsExactlyInAnyOrder(company)
		);
		verify(accountClient, times(1)).assignCompany(companyId);
	}

}
