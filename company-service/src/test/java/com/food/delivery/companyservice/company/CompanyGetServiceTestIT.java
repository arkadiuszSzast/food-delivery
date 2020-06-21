package com.food.delivery.companyservice.company;

import com.food.delivery.companyservice.support.CompanyServiceIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@CompanyServiceIntegrationTest
class CompanyGetServiceTestIT {

	@Autowired
	private CompanyGetService companyGetService;
	@Autowired
	private CompanyProvider companyProvider;

	@Test
	@DisplayName("Should return list of all companies")
	void shouldReturnListOfAllCompanies() {
		//arrange
		final var company1 = companyProvider.createAndSave();
		final var company2 = companyProvider.createAndSave();
		final var company3 = companyProvider.createAndSave();

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
		//act
		final var result = companyGetService.findAll().collectList().block();

		//assert
		assertThat(result).isEmpty();
	}

	@Test
	@DisplayName("Should return company by id")
	void shouldReturnCompanyById() {
		//arrange
		final var company = companyProvider.createAndSave();

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

		//act
		final var result = companyGetService.findById(notExistingId).blockOptional();

		//assert
		assertAll(
				() -> assertThat(result).isEmpty()
		);
	}

}
