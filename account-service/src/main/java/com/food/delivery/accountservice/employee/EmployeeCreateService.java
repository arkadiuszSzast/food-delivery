package com.food.delivery.accountservice.employee;

import com.food.delivery.accountservice.account.AccountRest;
import com.food.delivery.accountservice.company.CompanyGetService;
import com.food.delivery.accountservice.company.CompanyRest;
import com.food.delivery.accountservice.employee.domain.Employee;
import com.food.delivery.accountservice.employee.events.EmployeeActivateEvent;
import com.food.delivery.accountservice.employee.events.EmployeeActivateProducer;
import com.food.delivery.accountservice.user.events.UserActivateEvent;
import com.food.delivery.accountservice.user.okta.OktaAdapterAccountClient;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EmployeeCreateService {

	private final OktaAdapterAccountClient oktaAdapterAccountClient;
	private final EmployeeRepository employeeRepository;
	private final EmployeeActivateProducer employeeActivateProducer;
	private final EmployeeMapper employeeMapper;
	private final CompanyGetService companyGetService;

	public Mono<Employee> create(AccountRest accountRest, JwtAuthenticationToken principal) {
		return companyGetService.getCompany(principal.getName())
				.flatMap(companyRest -> create(accountRest, companyRest));
	}

	private Mono<Employee> create(AccountRest accountRest, CompanyRest companyRest) {
		return oktaAdapterAccountClient.createEmployee(accountRest)
				.map(oktaAccountRest -> employeeMapper.toDomain(oktaAccountRest, companyRest.getId()))
				.flatMap(employeeRepository::save)
				.doOnNext(employee -> produceSendEmployeeActivateEmail(employee, companyRest.getName()));
	}

	private void produceSendEmployeeActivateEmail(Employee employee, String companyName) {
		final var userActivateEvent = new UserActivateEvent(employee.getId(), employee.getEmail(),
				employee.getFirstName(), employee.getOktaId());
		final var employeeActivateEvent = new EmployeeActivateEvent(userActivateEvent, companyName);
		employeeActivateProducer.produceSendEmployeeActivateEmail(employeeActivateEvent);
	}
}
