package com.food.delivery.companyservice.company;

import com.food.delivery.companyservice.account.Account;
import com.food.delivery.companyservice.account.AccountClient;
import com.food.delivery.companyservice.account.AccountRest;
import com.food.delivery.companyservice.account.EmployeeRest;
import com.food.delivery.companyservice.company.model.CompanyRest;
import com.food.delivery.companyservice.utils.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyCreateServiceTest {

	@Mock
	private CompanyRepository companyRepository;
	@Mock
	private CompanyMapper companyMapper;
	@Mock
	private AccountClient accountClient;
	@InjectMocks
	private CompanyCreateService companyCreateService;
	private final CompanyFactory companyFactory = new CompanyFactory();

	@Test
	@DisplayName("Should create company")
	void shouldCreateCompany() {
		//arrange
		final var company = companyFactory.create();
		final var companyRest = new CompanyRest(company.getId(), company.getName(), company.getPhoneNumber());
		final var accountName = "accountName";
		final var surname = "surname";
		final var email = "name@mail.com";
		final var account = new Account(UUID.randomUUID().toString(), accountName, surname, email);
		final var employeeRest = new EmployeeRest(new AccountRest(account.getId(), accountName, surname, email), company.getName(), company.getId());
		final var jwtAuthenticationToken = new JwtAuthenticationToken(JwtProvider.getJwtWithSubject(email));

		when(accountClient.findEmployeeByEmail(email)).thenReturn(Mono.just(employeeRest));
		when(companyMapper.toDomain(companyRest, account.getId())).thenReturn(company);
		when(companyRepository.save(any())).thenReturn(Mono.just(company));
		when(accountClient.assignCompany(company.getId())).thenReturn(Mono.just(employeeRest));
		when(companyMapper.toRest(company)).thenReturn(companyRest);

		//act
		final var result = companyCreateService.create(jwtAuthenticationToken, companyRest).block();

		//assert
		assertThat(result).isEqualToComparingFieldByField(companyRest);
		verify(accountClient, times(1)).assignCompany(company.getId());
	}

}
