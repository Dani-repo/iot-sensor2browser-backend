package com.basic.sensor2browser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Sensor2Browser Spring Boot application.
 * Boots the REST API that receives MCU readings and exposes them to clients.
 */
@SpringBootApplication
public class Sensor2browserApplication {

	public static void main(String[] args) {
		SpringApplication.run(Sensor2browserApplication.class, args);
	}

}
