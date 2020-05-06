package com.cts.training.frontendservice.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cts.training.frontendservice.dto.Books;
import com.cts.training.frontendservice.dto.Delivery;
import com.cts.training.frontendservice.dto.UserBooks;
import com.cts.training.frontendservice.dto.Users;

@RestController
public class UserController {
	
	@Autowired
	RestTemplate restTemplate;
	
	
	
	@GetMapping("/getallusers")//-----> SHOWS ALL USERS
	public List<Users> printAll(){
		List<Users> list = new ArrayList<Users>();
		ResponseEntity<List<Users>> users = restTemplate.exchange("http://localhost:8000/users",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Users>>() {
        });
		users.getBody().forEach(e->list.add(e));
		return list;
	}
	
	
	
	@GetMapping("/getbooks") //------> SHOW ALL AVAILABLE BOOKS
	public List<Books> availableBooks(){
		List<Books> booklist = new ArrayList<Books>();
		ResponseEntity<List<Books>> books = restTemplate.exchange("http://localhost:8000/books",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Books>>() {
        });
		books.getBody().forEach(e->booklist.add(e));
		List<Books> booklist1 = booklist.stream().filter(e->e.getStock()>0).collect(Collectors.toList());
		return booklist1;
	}
	
	@GetMapping("/show-userbooks/{userid}") //-------> SHOWS BOOKS BORROWED BY USER
	public List<UserBooks> showUserBooks(@PathVariable int userid)
	{
		List<UserBooks> userbooks = restTemplate.exchange("http://localhost:8000/getall-userbooks",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<UserBooks>>() {
        }).getBody();
		userbooks = userbooks.stream().filter(e->e.getUserid()==userid).collect(Collectors.toList());
		return userbooks;
	}
	
	
	
	@GetMapping("/return/{tableid}") //-------> RETURN BOOKS BORROWED BY USER
	public Delivery bookReturn(@PathVariable int tableid) {
		
		int userid = restTemplate.exchange("http://localhost:8000/userbooks/"+tableid,
				 HttpMethod.GET, null, new ParameterizedTypeReference<UserBooks>() {
        }).getBody().getUserid();
		
		int bookid = restTemplate.exchange("http://localhost:8000/userbooks/"+tableid,
				 HttpMethod.GET, null, new ParameterizedTypeReference<UserBooks>() {
       }).getBody().getBookid();
		Delivery delivery = restTemplate.exchange("http://localhost:8000/delivery/details/"+userid+"/"+bookid,
				 HttpMethod.GET, null, new ParameterizedTypeReference<Delivery>() {
        }).getBody();
		delivery.setDeliverystatus(false);
		delivery.setDeliverytype("return");
		HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Delivery> requestEntity = new HttpEntity<>(delivery, requestHeaders);
		Delivery delivery2 = restTemplate.exchange("http://localhost:8000/delivery/",
				 HttpMethod.PUT, requestEntity, new ParameterizedTypeReference<Delivery>() {
        }).getBody();
		restTemplate.exchange("http://localhost:8000/userbooks/delete/"+tableid,
				 HttpMethod.DELETE, null, new ParameterizedTypeReference<UserBooks>() {
       });
		
		return delivery2;
	}
		

}
