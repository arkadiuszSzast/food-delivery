package com.food.delivery.mailsender.utils.kafka;

import com.food.delivery.mailsender.account.CompanyAdminRegisterEvent;
import com.food.delivery.mailsender.account.EmployeeActivateEvent;
import com.food.delivery.mailsender.account.UserActivateEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ActivateAccountReceiver {

	private static final String GROUP_ID_CONFIG_JSON = "json";
	private static final String AUTO_OFFSET_RESET_EARLIEST = "earliest";

	@Value("${spring.kafka.consumer.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, UserActivateEvent> userActivateKafkaListenerContainerFactory() {
		final var factory = new ConcurrentKafkaListenerContainerFactory<String, UserActivateEvent>();
		factory.setConsumerFactory(userActivateConsumerFactory());
		return factory;
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, EmployeeActivateEvent> employeeActivateKafkaListenerContainerFactory() {
		final var factory = new ConcurrentKafkaListenerContainerFactory<String, EmployeeActivateEvent>();
		factory.setConsumerFactory(employeeActivateConsumerFactory());
		return factory;
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, CompanyAdminRegisterEvent> companyAdminRegisterKafkaListenerContainerFactory() {
		final var factory = new ConcurrentKafkaListenerContainerFactory<String, CompanyAdminRegisterEvent>();
		factory.setConsumerFactory(companyAdminRegisterConsumerFactory());
		return factory;
	}

	private ConsumerFactory<String, UserActivateEvent> userActivateConsumerFactory() {
		return new DefaultKafkaConsumerFactory<>(
				consumerConfigs(),
				new StringDeserializer(),
				new JsonDeserializer<>(UserActivateEvent.class));
	}

	private ConsumerFactory<String, EmployeeActivateEvent> employeeActivateConsumerFactory() {
		return new DefaultKafkaConsumerFactory<>(
				consumerConfigs(),
				new StringDeserializer(),
				new JsonDeserializer<>(EmployeeActivateEvent.class));
	}

	private ConsumerFactory<String, CompanyAdminRegisterEvent> companyAdminRegisterConsumerFactory() {
		return new DefaultKafkaConsumerFactory<>(
				consumerConfigs(),
				new StringDeserializer(),
				new JsonDeserializer<>(CompanyAdminRegisterEvent.class));
	}

	private Map<String, Object> consumerConfigs() {
		return Map.of(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
				ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID_CONFIG_JSON,
				ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, AUTO_OFFSET_RESET_EARLIEST);
	}
}
