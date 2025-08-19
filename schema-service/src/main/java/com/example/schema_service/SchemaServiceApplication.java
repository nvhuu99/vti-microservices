package com.example.schema_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchemaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchemaServiceApplication.class, args);
		System.out.println("Schema updated!");
		System.exit(0);
	}

}
