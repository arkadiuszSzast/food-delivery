package com.food.delivery.companyservice.company;

import com.food.delivery.companyservice.account.Account;
import com.food.delivery.companyservice.company.domain.Company;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CompanyCreateService {

	private final CompanyRepository companyRepository;

	public Mono<Company> create(Account account, Company company) {
		final var accountId = account.getId();

		company.setCompanyAdminId(accountId);
		return companyRepository.save(company);
	}
}
