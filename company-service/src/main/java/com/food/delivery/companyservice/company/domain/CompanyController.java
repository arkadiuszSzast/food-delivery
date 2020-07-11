package com.food.delivery.companyservice.company.domain;

import com.food.delivery.companyservice.account.AccountClient;
import com.food.delivery.companyservice.company.CompanyCreateService;
import com.food.delivery.companyservice.company.CompanyGetService;
import com.food.delivery.companyservice.company.model.CompanyRest;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/company")
public class CompanyController {

	private final CompanyGetService companyGetService;
	private final CompanyCreateService companyCreateService;
	private final AccountClient accountClient;

	@GetMapping("/{companyId}")
	public Mono<CompanyRest> findById(@PathVariable String companyId) {
		return companyGetService.findById(companyId);
	}

	@GetMapping
	public Flux<CompanyRest> findAll() {
		return companyGetService.findAll();
	}

	@PostMapping
	@PreAuthorize("hasAuthority('COMPANY_ADMIN')")
	public Mono<CompanyRest> createCompany(@Valid @RequestBody CompanyRest companyRest) {
		return accountClient.findEmployeeMe()
				.switchIfEmpty(Mono.error(new UsernameNotFoundException("Failed to create company")))
				.flatMap(account -> companyCreateService.create(account, companyRest));
	}
}
