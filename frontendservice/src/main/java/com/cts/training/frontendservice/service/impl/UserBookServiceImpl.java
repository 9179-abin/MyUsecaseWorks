package com.cts.training.frontendservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cts.training.frontendservice.dto.UserBooks;
import com.cts.training.frontendservice.service.FrontendService;
import com.cts.training.frontendservice.service.UserBookService;

@Service
public class UserBookServiceImpl implements UserBookService {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	FrontendService frontEndService;
	
	
	@Value("${service.backendService.serviceId}")
	private String backendServiceId;
	
	public String basicUrl = "http://";

	@Override
	public List<UserBooks> getAllUserBooks() {
		String url = basicUrl+backendServiceId+"/getall-userbooks";
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<String> requestEntity = new HttpEntity<String>( header);
		
		List<UserBooks> userbooks = restTemplate.exchange(url,
                HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<UserBooks>>() {
        }).getBody();
		return userbooks;
	}

	@Override
	public void deleteUserBook(int tableid) {
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<String> requestEntity1 = new HttpEntity<String>( header);
		String url = basicUrl+backendServiceId+"/userbooks/delete/"+tableid;
		restTemplate.exchange(url,
				 HttpMethod.DELETE, requestEntity1, new ParameterizedTypeReference<UserBooks>() {
       });
		
	}

	@Override
	public UserBooks insertBook(UserBooks book) {
		HttpHeaders header = frontEndService.getAuthHeader();
		String url = basicUrl+backendServiceId+"/userbooks";
    	HttpEntity<UserBooks> requestEntity2 = new HttpEntity<>(book, header);
    	UserBooks userbook=restTemplate.exchange(url, HttpMethod.POST, requestEntity2,
				new ParameterizedTypeReference<UserBooks>() { }).getBody();
		return userbook;
	}

	@Override
	public UserBooks getBookById(int tableid) 
	{
		String url = basicUrl+backendServiceId+"/userbooks/"+tableid;
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<String> requestEntity = new HttpEntity<String>( header);
		UserBooks book = restTemplate.exchange(url,
				 HttpMethod.GET,  requestEntity, new ParameterizedTypeReference<UserBooks>() { }).getBody();
		return book;
	}

}
