package com.vishal.kafka.publisher.kafkapublisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class KafkaPublisherApplication {

	@Autowired
	private KafkaTemplate<String, Object> template;

	public static final String TOPIC = "first_topic";
	
	public static final String SECOND_TOPIC = "second_topic";


	@GetMapping("/publish/{name}")
	public String publishMessage(@PathVariable String name) {

		template.send(TOPIC, "Hi " + name + " Welcome to java techie");
		return "Data published";
	}
	
	@GetMapping("/publishJson")
	public String publishJson() {

		template.send("third_topic", new User(1,"vishal", new String[] {"Narsipatnam"}));
		return "Json Data published";
	}

	public static void main(String[] args) {
		SpringApplication.run(KafkaPublisherApplication.class, args);
	}

}
