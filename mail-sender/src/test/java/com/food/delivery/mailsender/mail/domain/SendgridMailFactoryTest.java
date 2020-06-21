package com.food.delivery.mailsender.mail.domain;

import com.food.delivery.mailsender.account.AccountActivateUrlProvider;
import com.food.delivery.mailsender.account.CompanyAdminRegisterEvent;
import com.food.delivery.mailsender.account.EmployeeActivateEvent;
import com.food.delivery.mailsender.account.UserActivateEvent;
import com.food.delivery.mailsender.jwt.JwtServiceClient;
import com.food.delivery.mailsender.utils.properties.SendgridProperties;
import com.food.delivery.mailsender.utils.properties.SendgridTemplatesProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SendgridMailFactoryTest {

	@Mock
	private SendgridProperties sendgridProperties;
	@Mock
	private SendgridTemplatesProperties sendgridTemplatesProperties;
	@Mock
	private JwtServiceClient jwtServiceClient;
	@Mock
	private AccountActivateUrlProvider accountActivateUrlProvider;
	@InjectMocks
	private SendgridMailFactory sendgridMailFactory;

	@Test
	@DisplayName("Should create user activation email")
	void shouldCreateUserActivationEmail() {
		//arrange
		final var firstName = "Joe";
		final var to = "joe@mail.com";
		final var oktaUserId = "oktaUserId";
		final var from = "sender@mail.com";
		final var confirmUserTemplateId = "confirmUserTemplateId";
		final var activateAccountJwt = "activateAccountJwt";
		final var activateUrl = "http://account-activate";
		final var accountActivateEvent = new UserActivateEvent(firstName, to, oktaUserId);

		when(jwtServiceClient.getUserActivateJwt(oktaUserId)).thenReturn(Mono.just(activateAccountJwt));
		when(accountActivateUrlProvider.getUserActivateUrl(activateAccountJwt)).thenReturn(activateUrl);
		when(sendgridProperties.getSender()).thenReturn(from);
		when(sendgridTemplatesProperties.getConfirmUserRegistration()).thenReturn(confirmUserTemplateId);

		//act
		final var result = sendgridMailFactory.getUserActivateMail(accountActivateEvent).block();

		//assert
		final var personalization = Objects.requireNonNull(result).getPersonalizations().iterator().next();
		assertAll(
				() -> assertThat(result).isNotNull(),
				() -> assertThat(result.getFrom()).isEqualToComparingFieldByField(new EmailWrapper(from)),
				() -> assertThat(result.getTemplateId()).isEqualTo(confirmUserTemplateId),
				() -> assertThat(personalization.getTo().iterator().next()).isEqualToComparingFieldByField(new EmailWrapper(to)),
				() -> assertThat(personalization.getDynamicTemplateData()).hasSize(2),
				() -> assertThat(personalization.getDynamicTemplateData().get("username")).isEqualTo(firstName),
				() -> assertThat(personalization.getDynamicTemplateData().get("confirm-url")).isEqualTo(activateUrl)
		);
	}

	@Test
	@DisplayName("Should create employee activation email")
	void shouldCreateEmployeeActivationEmail() {
		//arrange
		final var firstName = "Joe";
		final var to = "joe@mail.com";
		final var oktaUserId = "oktaUserId";
		final var from = "sender@mail.com";
		final var companyName = "companyName";
		final var confirmEmployeeTemplateId = "confirmUserTemplateId";
		final var activateEmployeeJwt = "activateAccountJwt";
		final var activateUrl = "http://account-activate";
		final var accountActivateEvent = new UserActivateEvent(firstName, to, oktaUserId);
		final var employeeActivateEvent = new EmployeeActivateEvent(accountActivateEvent, companyName);

		when(jwtServiceClient.getEmployeeActivateJwt(oktaUserId)).thenReturn(Mono.just(activateEmployeeJwt));
		when(accountActivateUrlProvider.getEmployeeActivateUrl(activateEmployeeJwt)).thenReturn(activateUrl);
		when(sendgridProperties.getSender()).thenReturn(from);
		when(sendgridTemplatesProperties.getConfirmEmployeeRegistration()).thenReturn(confirmEmployeeTemplateId);

		//act
		final var result = sendgridMailFactory.getEmployeeActivateMail(employeeActivateEvent).block();

		//assert
		final var personalization = Objects.requireNonNull(result).getPersonalizations().iterator().next();
		assertAll(
				() -> assertThat(result).isNotNull(),
				() -> assertThat(result.getFrom()).isEqualToComparingFieldByField(new EmailWrapper(from)),
				() -> assertThat(result.getTemplateId()).isEqualTo(confirmEmployeeTemplateId),
				() -> assertThat(personalization.getTo().iterator().next()).isEqualToComparingFieldByField(new EmailWrapper(to)),
				() -> assertThat(personalization.getDynamicTemplateData()).hasSize(3),
				() -> assertThat(personalization.getDynamicTemplateData().get("username")).isEqualTo(firstName),
				() -> assertThat(personalization.getDynamicTemplateData().get("company-name")).isEqualTo(companyName),
				() -> assertThat(personalization.getDynamicTemplateData().get("confirm-url")).isEqualTo(activateUrl)
		);
	}

	@Test
	@DisplayName("Should create company admin activation email")
	void shouldCreateCompanyAdminActivationEmail() {
		//arrange
		final var firstName = "Joe";
		final var to = "joe@mail.com";
		final var oktaUserId = "oktaUserId";
		final var from = "sender@mail.com";
		final var confirmCompanyAdminTemplateId = "confirmUserTemplateId";
		final var activateCompanyAdminJwt = "activateAccountJwt";
		final var activateUrl = "http://account-activate";
		final var accountActivateEvent = new UserActivateEvent(firstName, to, oktaUserId);

		when(jwtServiceClient.getCompanyAdminActivateJwt(oktaUserId)).thenReturn(Mono.just(activateCompanyAdminJwt));
		when(accountActivateUrlProvider.getCompanyAdminActivateUrl(activateCompanyAdminJwt)).thenReturn(activateUrl);
		when(sendgridProperties.getSender()).thenReturn(from);
		when(sendgridTemplatesProperties.getConfirmCompanyAdminRegistration()).thenReturn(confirmCompanyAdminTemplateId);

		//act
		final var result = sendgridMailFactory.getCompanyAdminActivateMail(accountActivateEvent).block();

		//assert
		final var personalization = Objects.requireNonNull(result).getPersonalizations().iterator().next();
		assertAll(
				() -> assertThat(result).isNotNull(),
				() -> assertThat(result.getFrom()).isEqualToComparingFieldByField(new EmailWrapper(from)),
				() -> assertThat(result.getTemplateId()).isEqualTo(confirmCompanyAdminTemplateId),
				() -> assertThat(personalization.getTo().iterator().next()).isEqualToComparingFieldByField(new EmailWrapper(to)),
				() -> assertThat(personalization.getDynamicTemplateData()).hasSize(2),
				() -> assertThat(personalization.getDynamicTemplateData().get("username")).isEqualTo(firstName),
				() -> assertThat(personalization.getDynamicTemplateData().get("confirm-url")).isEqualTo(activateUrl)
		);
	}

	@Test
	@DisplayName("Should create company admin register email")
	void shouldCreateCompanyAdminRegisterEmail() {
		//arrange
		final var to = "joe@mail.com";
		final var from = "sender@mail.com";
		final var confirmCompanyAdminTemplateId = "confirmUserTemplateId";
		final var activateCompanyAdminJwt = "activateAccountJwt";
		final var activateUrl = "http://account-activate";
		final var companyAdminRegisterEvent = new CompanyAdminRegisterEvent(to);

		when(jwtServiceClient.getCompanyAdminRegisterJwt(to)).thenReturn(Mono.just(activateCompanyAdminJwt));
		when(accountActivateUrlProvider.getCompanyAdminRegisterUrl(activateCompanyAdminJwt)).thenReturn(activateUrl);
		when(sendgridProperties.getSender()).thenReturn(from);
		when(sendgridTemplatesProperties.getCompanyAdminRegistration()).thenReturn(confirmCompanyAdminTemplateId);

		//act
		final var result = sendgridMailFactory.getCompanyAdminRegisterMail(companyAdminRegisterEvent).block();

		//assert
		final var personalization = Objects.requireNonNull(result).getPersonalizations().iterator().next();
		assertAll(
				() -> assertThat(result).isNotNull(),
				() -> assertThat(result.getFrom()).isEqualToComparingFieldByField(new EmailWrapper(from)),
				() -> assertThat(result.getTemplateId()).isEqualTo(confirmCompanyAdminTemplateId),
				() -> assertThat(personalization.getTo().iterator().next()).isEqualToComparingFieldByField(new EmailWrapper(to)),
				() -> assertThat(personalization.getDynamicTemplateData()).hasSize(1),
				() -> assertThat(personalization.getDynamicTemplateData().get("company-admin-register-url")).isEqualTo(activateUrl)
		);
	}
}
