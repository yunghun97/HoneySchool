package com.ssafy.honeySchool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.ssafy.db.repository"})
@EntityScan(basePackages = {"com.ssafy.db.entity"})
public class HoenySchoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(HoenySchoolApplication.class, args);
	}

}
