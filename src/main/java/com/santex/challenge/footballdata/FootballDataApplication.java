package com.santex.challenge.footballdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.ComponentScan;

@EnableCircuitBreaker
@SpringBootApplication
@EntityScan("com.santex.challenge.footballdata.domain")
@ComponentScan
public class FootballDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(FootballDataApplication.class, args);
	}

}
