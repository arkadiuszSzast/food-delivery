package com.food.delivery.mailsender.utils.kafka;

import com.food.delivery.mailsender.account.AccountActivateEvent;
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
public class ActivateUserReceiver {

	private static final String GROUP_ID_CONFIG_JSON = "json";
	private static final String AUTO_OFFSET_RESET_EARLIEST = "earliest";

	@Value("${spring.kafka.consumer.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, AccountActivateEvent> kafkaListenerContainerFactory() {
		final var factory = new ConcurrentKafkaListenerContainerFactory<String, AccountActivateEvent>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	public ConsumerFactory<String, AccountActivateEvent> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(
				consumerConfigs(),
				new StringDeserializer(),
				new JsonDeserializer<>(AccountActivateEvent.class));
	}

	private Map<String, Object> consumerConfigs() {
		return Map.of(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
				ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID_CONFIG_JSON,
				ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, AUTO_OFFSET_RESET_EARLIEST);
	}
}
