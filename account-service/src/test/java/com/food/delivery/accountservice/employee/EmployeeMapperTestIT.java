package com.food.delivery.accountservice.employee;

import com.food.delivery.accountservice.account.AccountRest;
import com.food.delivery.accountservice.employee.domain.Employee;
import com.food.delivery.accountservice.support.AccountServiceIntegrationTest;
import com.food.delivery.accountservice.user.okta.OktaAccountRest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static com.food.delivery.accountservice.utils.RandomStringProvider.randomString;
import static org.assertj.core.api.Assertions.assertThat;

@AccountServiceIntegrationTest
class EmployeeMapperTestIT {

	@Autowired
	private EmployeeMapper employeeMapper;

	@Test
	@DisplayName("Should map to domain form okta account rest")
	void shouldMapToDomainFromOktaAccount() {
		//arrange
		final var accountRest = new AccountRest(randomString(), randomString(), randomString());
		final var oktaAccountRest = new OktaAccountRest(accountRest, UUID.randomUUID().toString());
		final var employee = new Employee(null, accountRest.getFirstName(), accountRest.getLastName(), accountRest.getEmail(),
				oktaAccountRest.getOktaId(), null);

		//act
		final var result = employeeMapper.toDomain(oktaAccountRest);

		//assert
		assertThat(result).isEqualToComparingFieldByField(employee);
	}

	@Test
	@DisplayName("Should map to domain form okta account rest and company id")
	void shouldMapToDomainFromOktaAccountAndCompanyId() {
		//arrange
		final var companyId = randomString();
		final var accountRest = new AccountRest(randomString(), randomString(), randomString());
		final var oktaAccountRest = new OktaAccountRest(accountRest, UUID.randomUUID().toString());
		final var employee = new Employee(null, accountRest.getFirstName(), accountRest.getLastName(), accountRest.getEmail(),
				oktaAccountRest.getOktaId(), companyId);

		//act
		final var result = employeeMapper.toDomain(oktaAccountRest, companyId);

		//assert
		assertThat(result).isEqualToComparingFieldByField(employee);
	}

	@Test
	@DisplayName("Should map to rest")
	void shouldMapToRest() {
		//arrange
		final var companyId = randomString();
		final var employeeId = randomString();
		final var accountRest = new AccountRest(randomString(), randomString(), randomString());
		final var oktaAccountRest = new OktaAccountRest(accountRest, UUID.randomUUID().toString());
		final var employee = new Employee(employeeId, accountRest.getFirstName(), accountRest.getLastName(), accountRest.getEmail(),
				oktaAccountRest.getOktaId(), companyId);
		final var employeeRest = new EmployeeRest(accountRest, null, companyId);

		//act
		final var result = employeeMapper.toRest(employee);

		//assert
		assertThat(result).usingRecursiveComparison().isEqualTo(employeeRest);
	}

}
