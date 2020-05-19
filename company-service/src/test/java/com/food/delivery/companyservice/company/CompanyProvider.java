package com.food.delivery.companyservice.company;

import com.food.delivery.companyservice.company.domain.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyProvider {

	@Autowired
	private CompanyRepository companyRepository;
	private final CompanyFactory companyFactory = new CompanyFactory();

	public Company createAndSave() {
		return companyRepository.save(companyFactory.create()).block();
	}

}
