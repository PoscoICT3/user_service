package com.example.userService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	CommandLineRunner commandLineRunner(KafkaTemplate<String, Object> kafkaTemplate ) {
		return args -> {
			kafkaTemplate.send("jungTopic", "helloo");
		};
	}
}

