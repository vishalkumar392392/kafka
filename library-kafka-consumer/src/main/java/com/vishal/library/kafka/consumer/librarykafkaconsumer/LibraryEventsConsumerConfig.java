package com.vishal.library.kafka.consumer.librarykafkaconsumer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableKafka
@Slf4j
public class LibraryEventsConsumerConfig {

	@SuppressWarnings("deprecation")
	@Bean
	ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
			ConsumerFactory<Object, Object> kafkaConsumerFactory) {
		ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
		configurer.configure(factory, kafkaConsumerFactory);
		factory.setConcurrency(3);
//        factory.getContainerProperties().setAckMode(AckMode.MANUAL);
		factory.setErrorHandler((thrownException, data) -> {
			log.info("Exception in the consumer config is {} and record is {} ", thrownException.getMessage(), data);
		});
		factory.setRetryTemplate(retryTemplate());
		factory.setRecoveryCallback((context -> {
			if(context.getLastThrowable().getCause() instanceof RecoverableDataAccessException) {
				log.info("Inside the recoverable logic");
			}else {
				log.info("Inside the non recoverable logic");
				throw new RuntimeException(context.getLastThrowable().getMessage());
			}
			return null;
		}));
		return factory;
	}

	private RetryTemplate retryTemplate() {
		FixedBackOffPolicy policy = new FixedBackOffPolicy();
		policy.setBackOffPeriod(1000);
		RetryTemplate retryTemplate = new RetryTemplate();
		retryTemplate.setRetryPolicy(simpleRetryPolicy());
		retryTemplate.setBackOffPolicy(policy);
		return retryTemplate;
	}

	private RetryPolicy simpleRetryPolicy() {

//		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
//		retryPolicy.setMaxAttempts(3);
		Map<Class<? extends Throwable>, Boolean> exceptionsMap = new HashMap<>();
		exceptionsMap.put(IllegalArgumentException.class, false);
		exceptionsMap.put(RecoverableDataAccessException.class, true);
		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(3, exceptionsMap, true);
		return retryPolicy;
	}

}
