package com.ai_study_group.ia_analyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IaAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IaAnalyzerApplication.class, args);
	}

}
