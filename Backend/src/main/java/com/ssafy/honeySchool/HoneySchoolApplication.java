package com.ssafy.honeySchool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.ssafy.honeySchool.db.repository"})
@EntityScan(basePackages = {"com.ssafy.honeySchool.db.entity"})
public class HoneySchoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoneySchoolApplication.class, args);
	}

}
