package com.WebSocketBack.WSB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class WsbApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsbApplication.class, args);
	}

}
