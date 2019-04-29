package com.uwiseone.swp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MonitorScheduler {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Scheduled(cron = "* */10 * * * *")
	public void run() {
		System.out.println("============scheduler start");
        SeleniumTest selTest = new SeleniumTest();
        selTest.executeTestProcess();
	}
}
