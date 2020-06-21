package com.food.delivery.companyservice.company;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

	@Test
	@DisplayName("Should return company by id")
	void shouldReturnCompanyById() {
		//arrange
		final var company = companyFactory.create();
		when(companyRepository.findById(company.getId())).thenReturn(Mono.just(company));

		//act
		final var result = companyGetService.findById(company.getId()).blockOptional();

		//assert
		assertAll(
				() -> assertThat(result).isPresent(),
				() -> assertThat(result.get()).usingRecursiveComparison().isEqualTo(company)
		);
	}

	@Test
	@DisplayName("Should return empty when company not found by id")
	void shouldReturnEmptyWhenCompanyNotFoundById() {
		//arrange
		final var notExistingId = "notExistingId";
		when(companyRepository.findById(notExistingId)).thenReturn(Mono.empty());

		//act
		final var result = companyGetService.findById(notExistingId).blockOptional();

		//assert
		assertAll(
				() -> assertThat(result).isEmpty()
		);
	}

}
