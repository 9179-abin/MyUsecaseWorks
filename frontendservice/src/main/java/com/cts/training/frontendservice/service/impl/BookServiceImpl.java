package com.cts.training.frontendservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cts.training.frontendservice.dto.Books;
import com.cts.training.frontendservice.service.BookService;
import com.cts.training.frontendservice.service.FrontendService;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	FrontendService frontEndService;
	
	@Value("${service.backendService.serviceId}")
	private String backendServiceId;
	
	public String basicUrl = "http://";

	@Override
	public List<Books> getAllBooks() {
		List<Books> booklist = new ArrayList<Books>();
		String url = basicUrl+backendServiceId+"/books";
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<String> requestEntity = new HttpEntity<String>( header);
		
		booklist = restTemplate.exchange(url,
              HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Books>>() { }).getBody();
		return booklist;
	}

	@Override
	public Books updateStock(int bookid, String u_type) {
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<String> requestEntity = new HttpEntity<String>( header);
		String url =basicUrl+backendServiceId+"/books/"+bookid;
		Books book = restTemplate.exchange(url,
	              HttpMethod.GET, requestEntity, new ParameterizedTypeReference<Books>() { }).getBody();
		if(u_type.equals("incr")) {
			book.setStock(book.getStock()+1);
		}
		else {
			book.setStock(book.getStock()-1);
		}
		 HttpEntity<Books> requestEntity1 = new HttpEntity<>(book, header);
		 url = basicUrl+backendServiceId+"/books";
	     book =  restTemplate.exchange(url,
					 HttpMethod.PUT, requestEntity1, new ParameterizedTypeReference<Books>() { }).getBody();
	     return book;
	}

	@Override
	public Books getOneBook(int bookid) {
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<String> requestEntity = new HttpEntity<String>( header);
		String url =basicUrl+backendServiceId+"/books/"+bookid;
		Books book = restTemplate.exchange(url,
	              HttpMethod.GET, requestEntity, new ParameterizedTypeReference<Books>() { }).getBody();
		return book;
	}

}
