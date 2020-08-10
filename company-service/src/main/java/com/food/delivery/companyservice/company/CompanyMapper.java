package com.food.delivery.companyservice.company;

import com.food.delivery.companyservice.company.domain.Company;
import com.food.delivery.companyservice.company.model.CompanyRest;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyMapper {

	private final ModelMapper modelMapper;

	public Company toDomain(CompanyRest companyRest) {
		return modelMapper.map(companyRest, Company.CompanyBuilder.class)
				.build();
	}

	public Company toDomain(CompanyRest companyRest, String companyAdminId) {
		return modelMapper.map(companyRest, Company.CompanyBuilder.class)
				.companyAdminId(companyAdminId)
				.build();
	}

	public CompanyRest toRest(Company company) {
		return modelMapper.map(company, CompanyRest.CompanyRestBuilder.class)
				.build();
	}
}
