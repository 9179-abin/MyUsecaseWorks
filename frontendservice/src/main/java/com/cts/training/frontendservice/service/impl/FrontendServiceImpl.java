package com.cts.training.frontendservice.service.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.cts.training.frontendservice.config.FrontendConfiguration;
import com.cts.training.frontendservice.service.FrontendService;

@Service
public class FrontendServiceImpl implements FrontendService {
	
	@Autowired
	FrontendConfiguration configuration;

	@Override
	public HttpHeaders getAuthHeader() {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
        header.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		header.setBasicAuth(configuration.getBackendUsername(), configuration.getBackendPassword());
		return header;
	}

}
