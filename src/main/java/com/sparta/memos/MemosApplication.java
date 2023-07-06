package com.sparta.memos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class MemosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemosApplication.class, args);
	}

}
