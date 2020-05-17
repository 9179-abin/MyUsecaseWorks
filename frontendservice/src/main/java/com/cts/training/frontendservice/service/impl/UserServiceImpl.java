package com.cts.training.frontendservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cts.training.frontendservice.dto.Users;
import com.cts.training.frontendservice.service.FrontendService;
import com.cts.training.frontendservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	FrontendService frontEndService;
	
	
	@Value("${service.backendService.serviceId}")
	private String backendServiceId;
	
	public String basicUrl = "http://";

	@Override
	public ResponseEntity<Users> userLogin(String username, String password) {
		String url = basicUrl+backendServiceId+"/validuser/"+username+"/"+password;
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<String> requestEntity = new HttpEntity<String>( header);
		ResponseEntity<Users> responce = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<Users>() {
				});
		return responce;
	}

	@Override
	public List<Users> getAllUsers() {
		List<Users> list = new ArrayList<Users>();
		String url = basicUrl+backendServiceId+"/users";
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<String> requestEntity = new HttpEntity<String>( header);
		list = restTemplate.exchange(url,
                HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Users>>() { }).getBody();
		return list;
	}

	@Override
	public ResponseEntity<Users> registerUser(Users user) {
		String url = basicUrl+backendServiceId+"/users";
		HttpHeaders header = frontEndService.getAuthHeader();
		user.setUsertype("user");
		HttpEntity<Users> requestEntity = new HttpEntity<>(user,header);
		ResponseEntity<Users> responce = restTemplate
				.exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Users>() { });
		return responce;
	}

	@Override
	public Users getOneUser(int userid) {
		String url = basicUrl+backendServiceId+"/users/"+userid;
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<String> requestEntity = new HttpEntity<String>( header);
		Users user = restTemplate.exchange(url,
                HttpMethod.GET, requestEntity, new ParameterizedTypeReference<Users>() { }).getBody();
		return user;
	}

}
