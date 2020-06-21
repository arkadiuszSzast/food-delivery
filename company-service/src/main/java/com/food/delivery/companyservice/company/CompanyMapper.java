package com.food.delivery.companyservice.company;

import com.food.delivery.companyservice.company.domain.Company;
import com.food.delivery.companyservice.company.model.CompanyRest;
import org.springframework.stereotype.Service;

@Service
public class CompanyMapper {

	public Company toDomain(CompanyRest companyRest) {
		final var company = new Company();
		company.setName(companyRest.getName());
		company.setPhoneNumber(companyRest.getPhoneNumber());
		return company;
	}

	public CompanyRest toRest(Company company) {
		return new CompanyRest(company.getId(), company.getName(), company.getPhoneNumber());
	}
}
