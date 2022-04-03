package com.vishal.library.kafka.consumer.librarykafkaconsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LibraryKafkaConsumer {

	@KafkaListener(topics = {"library-events"})
	public void onMessage(ConsumerRecord<Integer, String> record) {
		
		log.info("Consumer record: {}", record);
		
	}
}
