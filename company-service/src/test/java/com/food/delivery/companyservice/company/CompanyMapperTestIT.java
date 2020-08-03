package com.food.delivery.companyservice.company;

import com.food.delivery.companyservice.company.domain.Company;
import com.food.delivery.companyservice.company.model.CompanyRest;
import com.food.delivery.companyservice.support.CompanyServiceIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@CompanyServiceIntegrationTest
class CompanyMapperTestIT {

	@Autowired
	private CompanyMapper companyMapper;

	@Test
	@DisplayName("Should map to domain")
	void shouldMapToDomain() {
		//arrange
		final var id = "id";
		final var name = "name";
		final var phoneNumber = "123123123";
		final var companyRest = new CompanyRest(id, name, phoneNumber);
		final var company = Company.builder()
				.id(id)
				.name(name)
				.phoneNumber(phoneNumber)
				.build();

		//act
		final var result = companyMapper.toDomain(companyRest);

		//assert
		assertThat(result).isEqualToComparingFieldByField(company);
	}

	@Test
	@DisplayName("Should map to rest")
	void shouldMapToRest() {
		//arrange
		final var id = "id";
		final var name = "name";
		final var phoneNumber = "123123123";
		final var companyRest = new CompanyRest(id, name, phoneNumber);
		final var company = Company.builder()
				.id(id)
				.name(name)
				.phoneNumber(phoneNumber)
				.build();

		//act
		final var result = companyMapper.toRest(company);

		//assert
		assertThat(result).isEqualToIgnoringGivenFields(companyRest, "validator");
	}

}
