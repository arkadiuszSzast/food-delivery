package com.food.delivery.mailsender.account;

import com.food.delivery.mailsender.mail.MailSender;
import com.food.delivery.mailsender.mail.domain.SendgridMail;
import com.food.delivery.mailsender.mail.domain.SendgridMailFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class AccountActivateConsumer {

	private static final String TOPIC_ACTIVATE_USER = "activate-user";
	private static final String TOPIC_ACTIVATE_EMPLOYEE = "activate-employee";
	private static final String TOPIC_ACTIVATE_COMPANY_ADMIN = "activate-company-admin";
	private static final String TOPIC_COMPANY_ADMIN_REGISTER = "company-admin-register";

	private final MailSender mailSender;
	private final SendgridMailFactory sendgridMailFactory;

	@KafkaListener(id = "activate-user-consumer", topicPattern = TOPIC_ACTIVATE_USER,
			containerFactory = "userActivateKafkaListenerContainerFactory")
	public void consumeActivateUser(@Payload UserActivateEvent userActivateEvent) {
		sendMail(sendgridMailFactory.getUserActivateMail(userActivateEvent));
	}

	@KafkaListener(id = "activate-employee-consumer", topicPattern = TOPIC_ACTIVATE_EMPLOYEE,
			containerFactory = "employeeActivateKafkaListenerContainerFactory")
	public void consumeActivateEmployee(@Payload EmployeeActivateEvent employeeActivateEvent) {
		sendMail(sendgridMailFactory.getEmployeeActivateMail(employeeActivateEvent));
	}

	@KafkaListener(id = "activate-company-admin-consumer", topicPattern = TOPIC_ACTIVATE_COMPANY_ADMIN,
			containerFactory = "userActivateKafkaListenerContainerFactory")
	public void consumeActivateCompanyAdmin(@Payload UserActivateEvent userActivateEvent) {
		sendMail(sendgridMailFactory.getCompanyAdminActivateMail(userActivateEvent));
	}

	@KafkaListener(id = "company-admin-register-consumer", topicPattern = TOPIC_COMPANY_ADMIN_REGISTER,
			containerFactory = "companyAdminRegisterKafkaListenerContainerFactory")
	public void consumeCompanyAdminRegister(@Payload CompanyAdminRegisterEvent companyAdminRegisterEvent) {
		sendMail(sendgridMailFactory.getCompanyAdminRegisterMail(companyAdminRegisterEvent));
	}

	private Disposable sendMail(Mono<SendgridMail> sendgridMailMono) {
		return sendgridMailMono
				.flatMap(mailSender::send)
				.doOnSuccess(data -> log.info("Mail send success"))
				.subscribe();
	}

}
