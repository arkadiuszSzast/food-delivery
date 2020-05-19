package com.food.delivery.companyservice.company;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyGetServiceTest {

	@Mock
	private CompanyRepository companyRepository;
	@InjectMocks
	private CompanyGetService companyGetService;
	private final CompanyFactory companyFactory = new CompanyFactory();

	@Test
	@DisplayName("Should return list of all companies")
	void shouldReturnListOfAllCompanies() {
		//arrange
		final var company1 = companyFactory.create();
		final var company2 = companyFactory.create();
		final var company3 = companyFactory.create();
		when(companyRepository.findAll()).thenReturn(Flux.just(company1, company2, company3));

		//act
		final var result = companyGetService.findAll().collectList().block();

		//assert
		assertAll(
				() -> assertThat(result).hasSize(3),
				() -> assertThat(result)
						.usingRecursiveFieldByFieldElementComparator()
						.containsExactlyInAnyOrder(company1, company2, company3)
		);
	}

	@Test
	@DisplayName("Should return empty list when no company found")
	void shouldReturnEmptyListWhenNoCompanyFound() {
		//arrange
		when(companyRepository.findAll()).thenReturn(Flux.empty());

		//act
		final var result = companyGetService.findAll().collectList().block();

		//assert
		assertThat(result).isEmpty();
	}
}
