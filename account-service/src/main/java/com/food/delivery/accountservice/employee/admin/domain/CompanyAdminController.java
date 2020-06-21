package com.food.delivery.accountservice.employee.admin.domain;

import com.food.delivery.accountservice.account.AccountActivation;
import com.food.delivery.accountservice.account.AccountRest;
import com.food.delivery.accountservice.employee.EmployeeRest;
import com.food.delivery.accountservice.employee.admin.CompanyAdminActivateService;
import com.food.delivery.accountservice.employee.admin.CompanyAdminCreateService;
import com.food.delivery.accountservice.employee.domain.Employee;
import com.food.delivery.accountservice.jwt.JwtServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/account/employee/company-admin")
public class CompanyAdminController {

	private final CompanyAdminCreateService companyAdminCreateService;
	private final JwtServiceClient jwtServiceClient;
	private final CompanyAdminActivateService companyAdminActivateService;

	@PostMapping("/invitation-email")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Mono<Void> initCompanyAdminRegister(@RequestParam String email) {
		return companyAdminCreateService.initRegister(email);
	}

	@PostMapping
	public Mono<Employee> create(@RequestBody AccountRest accountRest,
								 @RequestParam String token) {
		return jwtServiceClient.validateCompanyAdminRegisterToken(token)
				.flatMap(__ -> companyAdminCreateService.register(accountRest));
	}

	@PatchMapping("/activate")
	public Mono<AccountActivation> activateAccount(@RequestParam String token) {
		return companyAdminActivateService.activateAccount(token);
	}

	@PatchMapping("/{companyId}")
	@PreAuthorize("hasAuthority('COMPANY_ADMIN')")
	public Mono<EmployeeRest> assignCompany(@AuthenticationPrincipal JwtAuthenticationToken principal,
											@PathVariable String companyId) {
		return companyAdminCreateService.assignCompany(principal, companyId);
	}
}
