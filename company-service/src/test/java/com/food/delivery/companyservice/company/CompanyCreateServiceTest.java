package com.food.delivery.companyservice.company;

import com.food.delivery.companyservice.account.Account;
import com.food.delivery.companyservice.account.AccountClient;
import com.food.delivery.companyservice.company.model.CompanyRest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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
		when(companyMapper.toDomain(companyRest)).thenReturn(company);
		when(companyRepository.save(company)).thenReturn(Mono.just(company));
		when(accountClient.assignCompany(company.getId())).thenReturn(Mono.just(account));

		//act
		final var result = companyCreateService.create(account, companyRest).block();

		//assert
		assertThat(result).isEqualToComparingFieldByField(company);
	}

}
