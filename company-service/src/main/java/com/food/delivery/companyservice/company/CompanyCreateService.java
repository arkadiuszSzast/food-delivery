package com.food.delivery.companyservice.company;

import com.food.delivery.companyservice.account.Account;
import com.food.delivery.companyservice.company.domain.Company;
import com.food.delivery.companyservice.company.model.CompanyRest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CompanyCreateService {

	private final CompanyRepository companyRepository;
	private final CompanyMapper companyMapper;

	public Mono<Company> create(Account account, CompanyRest companyRest) {
		final var accountId = account.getId();
		final var company = companyMapper.toDomain(companyRest);

		company.setCompanyAdminId(accountId);
		return companyRepository.save(company);
	}
}
