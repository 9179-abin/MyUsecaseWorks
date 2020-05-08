package com.cts.training.backendservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("backend-service")
public class BackendConfiguration {
	
	@Value("${authUsername}")
	private String authUsername;
	
	@Value("${authPassword}")
	private String authPassword;
	
	public String getAuthUsername() {
		return authUsername;
	}

	public String getAuthPassword() {
		return authPassword;
	}

}
