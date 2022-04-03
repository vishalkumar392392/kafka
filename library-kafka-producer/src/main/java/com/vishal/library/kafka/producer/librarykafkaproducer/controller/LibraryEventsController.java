package com.vishal.library.kafka.producer.librarykafkaproducer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vishal.library.kafka.producer.librarykafkaproducer.model.LibraryEvent;
import com.vishal.library.kafka.producer.librarykafkaproducer.model.LibraryEventType;
import com.vishal.library.kafka.producer.librarykafkaproducer.producer.LibraryProducer;

@RestController
public class LibraryEventsController {
	
	@Autowired
	private LibraryProducer producer;
	
	@PostMapping("/v1/libraryevent")
	public ResponseEntity<LibraryEvent> postLibraryEvent(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException{
		libraryEvent.setLibraryEventType(LibraryEventType.NEW);
		producer.sendLibraryEvent_Approach2(libraryEvent);
		return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
	}
	
	@PutMapping("/v1/libraryevent")
	public ResponseEntity<?> putLibraryEvent(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException{
		if(libraryEvent.getLibraryEvent()==null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("please pass the library event id");
		}
		libraryEvent.setLibraryEventType(LibraryEventType.UPDATE);
		producer.sendLibraryEvent_Approach2(libraryEvent);
		return ResponseEntity.status(HttpStatus.OK).body(libraryEvent);
	}

}
