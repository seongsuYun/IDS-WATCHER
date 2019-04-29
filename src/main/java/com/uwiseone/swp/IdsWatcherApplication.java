package com.uwiseone.swp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IdsWatcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdsWatcherApplication.class, args);
		
        //SeleniumTest selTest = new SeleniumTest();
        //selTest.executeTestProcess();
	}

}
