package com.banmoon.meeting_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.banmoon.meeting_management")
public class MeetingManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetingManagementApplication.class, args);
	}

}
