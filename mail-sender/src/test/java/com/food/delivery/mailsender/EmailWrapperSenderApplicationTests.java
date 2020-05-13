package com.food.delivery.mailsender;

import com.food.delivery.mailsender.mail.MailSender;
import com.food.delivery.mailsender.support.MailSenderIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@MailSenderIntegrationTest
class EmailWrapperSenderApplicationTests {

	@Autowired
	private MailSender mailSender;

	@Test
	@DisplayName("Should load context")
	void contextLoads() {
		assertThat(mailSender).isNotNull();
	}

}
