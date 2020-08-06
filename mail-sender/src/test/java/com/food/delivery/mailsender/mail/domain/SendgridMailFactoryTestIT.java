package com.food.delivery.mailsender.mail.domain;

import com.food.delivery.mailsender.account.CompanyAdminRegisterEvent;
import com.food.delivery.mailsender.account.EmployeeActivateEvent;
import com.food.delivery.mailsender.account.UserActivateEvent;
import com.food.delivery.mailsender.jwt.JwtServiceClient;
import com.food.delivery.mailsender.support.MailSenderIntegrationTest;
import com.food.delivery.mailsender.utils.properties.SendgridProperties;
import com.food.delivery.mailsender.utils.properties.SendgridTemplatesProperties;
import com.food.delivery.mailsender.utils.properties.UrlsProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@MailSenderIntegrationTest
class SendgridMailFactoryTestIT {

	@Autowired
	private SendgridMailFactory sendgridMailFactory;
	@Autowired
	private SendgridTemplatesProperties sendgridTemplatesProperties;
	@Autowired
	private SendgridProperties sendgridProperties;
	@Autowired
	private UrlsProperties urlsProperties;
	@MockBean(name = "com.food.delivery.mailsender.jwt.JwtServiceClient")
	private JwtServiceClient jwtServiceClient;

	@Test
	@DisplayName("Should create user activation email")
	void shouldCreateUserActivationEmail() {
		//arrange
		final var firstName = "Joe";
		final var to = "joe@mail.com";
		final var oktaUserId = "oktaUserId";
		final var activateAccountJwt = "activateAccountJwt";
		final var accountActivateEvent = new UserActivateEvent(firstName, to, oktaUserId);

		when(jwtServiceClient.getUserActivateJwt(oktaUserId)).thenReturn(Mono.just(activateAccountJwt));

		//act
		final var result = sendgridMailFactory.getUserActivateMail(accountActivateEvent).block();

		//assert
		final var personalization = Objects.requireNonNull(result).getPersonalizations().iterator().next();
		assertAll(
				() -> assertThat(result).isNotNull(),
				() -> assertThat(result.getFrom())
						.isEqualToComparingFieldByField(new EmailWrapper(sendgridProperties.getSender())),
				() -> assertThat(result.getTemplateId())
						.isEqualTo(sendgridTemplatesProperties.getConfirmUserRegistration()),
				() -> assertThat(personalization.getTo().iterator().next())
						.isEqualToComparingFieldByField(new EmailWrapper(to)),
				() -> assertThat(personalization.getDynamicTemplateData())
						.hasSize(2),
				() -> assertThat(personalization.getDynamicTemplateData().get("username"))
						.isEqualTo(firstName),
				() -> assertThat(personalization.getDynamicTemplateData().get("confirm-url"))
						.contains(urlsProperties.getUserActivateUrl(), activateAccountJwt)
		);
	}

	@Test
	@DisplayName("Should create employee activation email")
	void shouldCreateEmployeeActivationEmail() {
		//arrange
		final var firstName = "Joe";
		final var to = "joe@mail.com";
		final var oktaUserId = "oktaUserId";
		final var activateAccountJwt = "activateAccountJwt";
		final var companyName = "companyName";
		final var accountActivateEvent = new UserActivateEvent(firstName, to, oktaUserId);
		final var employeeActivateEvent = new EmployeeActivateEvent(accountActivateEvent, companyName);

		when(jwtServiceClient.getEmployeeActivateJwt(oktaUserId)).thenReturn(Mono.just(activateAccountJwt));

		//act
		final var result = sendgridMailFactory.getEmployeeActivateMail(employeeActivateEvent).block();

		//assert
		final var personalization = Objects.requireNonNull(result).getPersonalizations().iterator().next();
		assertAll(
				() -> assertThat(result).isNotNull(),
				() -> assertThat(result.getFrom())
						.isEqualToComparingFieldByField(new EmailWrapper(sendgridProperties.getSender())),
				() -> assertThat(result.getTemplateId())
						.isEqualTo(sendgridTemplatesProperties.getConfirmEmployeeRegistration()),
				() -> assertThat(personalization.getTo().iterator().next())
						.isEqualToComparingFieldByField(new EmailWrapper(to)),
				() -> assertThat(personalization.getDynamicTemplateData())
						.hasSize(3),
				() -> assertThat(personalization.getDynamicTemplateData().get("username"))
						.isEqualTo(firstName),
				() -> assertThat(personalization.getDynamicTemplateData().get("company-name"))
						.isEqualTo(companyName),
				() -> assertThat(personalization.getDynamicTemplateData().get("confirm-url"))
						.contains(urlsProperties.getEmployeeActivateUrl(), activateAccountJwt)
		);
	}

	@Test
	@DisplayName("Should create company admin activation email")
	void shouldCreateCompanyAdminActivationEmail() {
		//arrange
		final var firstName = "Joe";
		final var to = "joe@mail.com";
		final var oktaUserId = "oktaUserId";
		final var activateAccountJwt = "activateAccountJwt";
		final var accountActivateEvent = new UserActivateEvent(firstName, to, oktaUserId);

		when(jwtServiceClient.getCompanyAdminActivateJwt(oktaUserId)).thenReturn(Mono.just(activateAccountJwt));

		//act
		final var result = sendgridMailFactory.getCompanyAdminActivateMail(accountActivateEvent).block();

		//assert
		final var personalization = Objects.requireNonNull(result).getPersonalizations().iterator().next();
		assertAll(
				() -> assertThat(result).isNotNull(),
				() -> assertThat(result.getFrom())
						.isEqualToComparingFieldByField(new EmailWrapper(sendgridProperties.getSender())),
				() -> assertThat(result.getTemplateId())
						.isEqualTo(sendgridTemplatesProperties.getConfirmCompanyAdminRegistration()),
				() -> assertThat(personalization.getTo().iterator().next())
						.isEqualToComparingFieldByField(new EmailWrapper(to)),
				() -> assertThat(personalization.getDynamicTemplateData())
						.hasSize(2),
				() -> assertThat(personalization.getDynamicTemplateData().get("username"))
						.isEqualTo(firstName),
				() -> assertThat(personalization.getDynamicTemplateData().get("confirm-url"))
						.contains(urlsProperties.getCompanyAdminActivateUrl(), activateAccountJwt)
		);
	}

	@Test
	@DisplayName("Should create company admin registration email")
	void shouldCreateCompanyAdminRegistrationEmail() {
		//arrange
		final var to = "joe@mail.com";
		final var activateAccountJwt = "activateAccountJwt";
		final var companyAdminRegisterEvent = new CompanyAdminRegisterEvent(to);

		when(jwtServiceClient.getCompanyAdminRegisterJwt(to)).thenReturn(Mono.just(activateAccountJwt));

		//act
		final var result = sendgridMailFactory.getCompanyAdminRegisterMail(companyAdminRegisterEvent).block();

		//assert
		final var personalization = Objects.requireNonNull(result).getPersonalizations().iterator().next();
		assertAll(
				() -> assertThat(result).isNotNull(),
				() -> assertThat(result.getFrom())
						.isEqualToComparingFieldByField(new EmailWrapper(sendgridProperties.getSender())),
				() -> assertThat(result.getTemplateId())
						.isEqualTo(sendgridTemplatesProperties.getCompanyAdminRegistration()),
				() -> assertThat(personalization.getTo().iterator().next())
						.isEqualToComparingFieldByField(new EmailWrapper(to)),
				() -> assertThat(personalization.getDynamicTemplateData())
						.hasSize(1),
				() -> assertThat(personalization.getDynamicTemplateData().get("company-admin-register-url"))
						.contains(urlsProperties.getCompanyAdminRegisterUrl(), activateAccountJwt)
		);
	}

}
