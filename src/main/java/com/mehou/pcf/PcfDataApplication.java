package com.mehou.pcf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PcfDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(PcfDataApplication.class, args);
	}
}
