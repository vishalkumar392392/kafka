package com.vishal.library.kafka.producer.librarykafkaproducer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LibraryEvent {

	private Integer libraryEvent;
	private LibraryEventType libraryEventType;
	private Book book;
}
