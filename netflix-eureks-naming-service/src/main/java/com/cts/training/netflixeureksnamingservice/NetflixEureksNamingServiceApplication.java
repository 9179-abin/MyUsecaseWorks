package com.cts.training.netflixeureksnamingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class NetflixEureksNamingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetflixEureksNamingServiceApplication.class, args);
	}

}
