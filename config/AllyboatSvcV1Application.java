
package com.technojade.allybot.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(basePackages = {"com.technojade.allybot"})
@EnableJpaRepositories(basePackages = {"com.technojade.allybot"})
@EntityScan(basePackages = {"com.technojade.allybot.*"})
@EnableAutoConfiguration
public class AllyboatSvcV1Application {

	public static void main(String[] args) {
		SpringApplication.run(AllyboatSvcV1Application.class, args);
	}
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}

