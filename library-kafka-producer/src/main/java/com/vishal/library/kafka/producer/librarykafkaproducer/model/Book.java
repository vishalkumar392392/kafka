package com.vishal.library.kafka.producer.librarykafkaproducer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

	private Integer bookId;
	private String bookName;
	private String bookAuthor;

}
