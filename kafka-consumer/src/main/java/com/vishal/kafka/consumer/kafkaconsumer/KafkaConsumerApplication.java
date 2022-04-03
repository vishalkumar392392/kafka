package com.vishal.kafka.consumer.kafkaconsumer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class KafkaConsumerApplication {

	private List<String> messages = new ArrayList<String>();

	private User userData = null;

	@GetMapping("/consume")
	public List<String> consume() {
		return messages;
	}

	@GetMapping("/consumeJson")
	public User consumeJson() {
		return userData;
	}

//	@KafkaListener(groupId = "my-first-consumer-group", topics = "first_topic", containerFactory = "kafkaListenerContainerFactory")
//	public List<String> getMsgFromTopic(String data){
//		messages.add(data);
//		return messages;
//	}

	@KafkaListener(groupId = "my-third-consumer-group", topics = "third_topic", containerFactory = "userKafkaListenerContainerFactory")
	public User getJsonMsgFromTopic(User user) {
		userData = user;
		System.out.println("user: " + user);
		return userData;
	}

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerApplication.class, args);
	}

}
