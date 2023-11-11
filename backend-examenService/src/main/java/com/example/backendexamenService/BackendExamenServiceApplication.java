package com.example.backendexamenService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BackendExamenServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendExamenServiceApplication.class, args);
	}

}
