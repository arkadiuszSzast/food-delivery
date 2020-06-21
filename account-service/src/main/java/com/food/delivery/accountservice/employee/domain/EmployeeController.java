package com.food.delivery.accountservice.employee.domain;

import com.food.delivery.accountservice.account.AccountActivation;
import com.food.delivery.accountservice.account.AccountRest;
import com.food.delivery.accountservice.employee.EmployeeActivateService;
import com.food.delivery.accountservice.employee.EmployeeCreateService;
import com.food.delivery.accountservice.employee.EmployeeGetService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/account/employee")
public class EmployeeController {

	private final EmployeeCreateService employeeCreateService;
	private final EmployeeActivateService employeeActivateService;
	private final EmployeeGetService employeeGetService;

	@PostMapping
	@PreAuthorize("hasAuthority('COMPANY_ADMIN')")
	public Mono<Employee> create(@RequestBody AccountRest employeeRest,
								 @AuthenticationPrincipal JwtAuthenticationToken principal) {
		return employeeCreateService.create(employeeRest, principal);
	}

	@GetMapping("/me")
	public Mono<Employee> findMe(@AuthenticationPrincipal JwtAuthenticationToken principal) {
		return employeeGetService.findByEmail(principal.getName());
	}

	@PatchMapping("/activate")
	public Mono<AccountActivation> activateAccount(@RequestParam String token) {
		return employeeActivateService.activateAccount(token);
	}
}
