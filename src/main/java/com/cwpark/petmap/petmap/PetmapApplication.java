package com.cwpark.petmap.petmap;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBatchProcessing // 배치
@EnableScheduling // 스케쥴러
@EnableJpaRepositories(basePackages = {"com.cwpark.petmap.petmap.data.persistence"})
public class PetmapApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetmapApplication.class, args);
	}

}
