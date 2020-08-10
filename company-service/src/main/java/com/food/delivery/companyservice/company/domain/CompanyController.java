package com.food.delivery.companyservice.company.domain;

import com.food.delivery.companyservice.company.CompanyCreateService;
import com.food.delivery.companyservice.company.CompanyGetService;
import com.food.delivery.companyservice.company.CompanyValidationException;
import com.food.delivery.companyservice.company.model.CompanyRest;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/company")
public class CompanyController {

	private final CompanyGetService companyGetService;
	private final CompanyCreateService companyCreateService;

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
	public Mono<CompanyRest> createCompany(@AuthenticationPrincipal JwtAuthenticationToken principal, @RequestBody CompanyRest companyRest) {
		return companyRest.validate().fold(v -> Mono.error(new CompanyValidationException()),
				company -> companyCreateService.create(principal, companyRest));
	}
}
