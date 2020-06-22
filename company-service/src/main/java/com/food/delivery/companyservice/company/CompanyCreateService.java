package com.food.delivery.companyservice.company;

import com.food.delivery.companyservice.account.Account;
import com.food.delivery.companyservice.account.AccountClient;
import com.food.delivery.companyservice.company.model.CompanyRest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CompanyCreateService {

	private final AccountClient accountClient;
	private final CompanyRepository companyRepository;
	private final CompanyMapper companyMapper;

	public Mono<CompanyRest> create(Account account, CompanyRest companyRest) {
		final var accountId = account.getId();
		return Mono.just(companyMapper.toDomain(companyRest))
				.doOnNext(company -> company.setCompanyAdminId(accountId))
				.flatMap(companyRepository::save)
				.doOnNext(company -> accountClient.assignCompany(company.getId()))
				.map(companyMapper::toRest);
	}

}
