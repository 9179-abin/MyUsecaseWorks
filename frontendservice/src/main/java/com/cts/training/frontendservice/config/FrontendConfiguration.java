package com.cts.training.frontendservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("frontend-service")
public class FrontendConfiguration {
	
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
	
	@Value("${backendUsername}")
	private String backendUsername;
	
	@Value("${backendPassword}")
	private String backendPassword;
	
	public String getBackendUsername() {
		return backendUsername;
	}

	public String getBackendPassword() {
		return backendPassword;
	}
	

}
