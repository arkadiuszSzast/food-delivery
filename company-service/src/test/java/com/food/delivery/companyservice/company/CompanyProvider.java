package com.food.delivery.companyservice.company;

import com.food.delivery.companyservice.company.model.CompanyRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CompanyProvider {

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private CompanyMapper companyMapper;
	private final CompanyFactory companyFactory = new CompanyFactory();

	public CompanyRest createAndSave() {
		return companyRepository.save(companyFactory.create())
				.map(companyMapper::toRest)
				.block();
	}

}
