package com.food.delivery.companyservice.company.domain;

import com.food.delivery.companyservice.account.AccountClient;
import com.food.delivery.companyservice.company.CompanyCreateService;
import com.food.delivery.companyservice.company.CompanyGetService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/company")
public class CompanyController {

	private final CompanyGetService companyGetService;
	private final CompanyCreateService companyCreateService;
	private final AccountClient accountClient;


	@GetMapping
	public Flux<Company> findAll() {
		return companyGetService.findAll();
	}

	@PostMapping
	@PreAuthorize("hasAuthority('COMPANY_ADMIN')")
	public Mono<Company> test(Company company) {
		return accountClient.findAccount()
				.flatMap(account -> companyCreateService.create(account, company));
	}
}
