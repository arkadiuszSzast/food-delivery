package com.food.delivery.companyservice.company;

import com.food.delivery.companyservice.account.AccountClient;
import com.food.delivery.companyservice.company.model.CompanyRest;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CompanyCreateService {

	private final AccountClient accountClient;
	private final CompanyRepository companyRepository;
	private final CompanyMapper companyMapper;

	public Mono<CompanyRest> create(JwtAuthenticationToken principal, CompanyRest companyRest) {
		return accountClient.findEmployeeByEmail(principal.getName())
				.map(account -> companyMapper.toDomain(companyRest, account.getAccountRest().getId()))
				.flatMap(companyRepository::save)
				.map(companyMapper::toRest)
				.flatMap(company -> accountClient.assignCompany(company.getId())
						.then(Mono.just(company)));
	}

}

