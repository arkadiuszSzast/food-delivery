package com.food.delivery.accountservice.employee.admin;

import com.food.delivery.accountservice.account.AccountRest;
import com.food.delivery.accountservice.employee.EmployeeMapper;
import com.food.delivery.accountservice.employee.EmployeeRepository;
import com.food.delivery.accountservice.employee.EmployeeRest;
import com.food.delivery.accountservice.employee.admin.model.CompanyAdminActivateProducer;
import com.food.delivery.accountservice.employee.domain.Employee;
import com.food.delivery.accountservice.employee.events.CompanyAdminRegisterEvent;
import com.food.delivery.accountservice.employee.events.CompanyAdminRegisterProducer;
import com.food.delivery.accountservice.user.events.UserActivateEvent;
import com.food.delivery.accountservice.user.okta.OktaAdapterAccountClient;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CompanyAdminCreateService {

	private final OktaAdapterAccountClient oktaAdapterAccountClient;
	private final EmployeeRepository employeeRepository;
	private final CompanyAdminActivateProducer companyAdminActivateProducer;
	private final CompanyAdminRegisterProducer companyAdminRegisterProducer;
	private final EmployeeMapper employeeMapper;

	public Mono<Void> initRegister(String email) {
		return Mono.fromRunnable(() -> produceSendCompanyAdminRegisterEmail(email));
	}

	private void produceSendCompanyAdminRegisterEmail(String email) {
		final var companyAdminRegisterEvent = new CompanyAdminRegisterEvent(email);
		companyAdminRegisterProducer.produceSendCompanyAdminRegisterEmail(companyAdminRegisterEvent);
	}

	public Mono<Employee> register(AccountRest accountRest) {
		return oktaAdapterAccountClient.createCompanyAdmin(accountRest)
				.map(employeeMapper::toDomain)
				.flatMap(employeeRepository::save)
				.doOnSuccess(this::produceSendCompanyAdminActivateEmail);
	}

	public Mono<EmployeeRest> assignCompany(JwtAuthenticationToken principal, String companyId) {
		return employeeRepository.findByEmail(principal.getName())
				.map(employee -> setCompany(companyId, employee))
				.flatMap(employeeRepository::save)
				.map(employeeMapper::toRest);
	}

	private void produceSendCompanyAdminActivateEmail(Employee employee) {
		final var accountActivateEvent = new UserActivateEvent(employee.getId(), employee.getEmail(),
				employee.getFirstName(), employee.getOktaId());
		companyAdminActivateProducer.produceSendCompanyAdminActivateEmail(accountActivateEvent);
	}

	private Employee setCompany(String companyId, Employee employee) {
		employee.setCompanyId(companyId);
		return employee;
	}
}
