package com.boltonuni.devop7303;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Devop7303Application {

	public static void main(String[] args) {
		SpringApplication.run(Devop7303Application.class, args);
	}

}
