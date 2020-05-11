package com.cts.training.frontendservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cts.training.frontendservice.dto.Books;
import com.cts.training.frontendservice.dto.Delivery;
import com.cts.training.frontendservice.dto.Orders;
import com.cts.training.frontendservice.dto.Users;
import com.cts.training.frontendservice.service.FrontendService;

@RestController
@RequestMapping("/librarian")
public class LibrarianController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	FrontendService frontEndService;
	
	@Value("${service.backendService.serviceId}")
	private String backendServiceId;
	
	public String basicUrl = "http://";
	
	
	@GetMapping("/getallusers")//-----> SHOWS ALL USERS
	public List<Users> printAll(){
		List<Users> list = new ArrayList<Users>();
		String url = basicUrl+backendServiceId+"/users";
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<String> requestEntity = new HttpEntity<String>( header);
		list = restTemplate.exchange(url,
                HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Users>>() { }).getBody();
		return list;
	}
	
	
	@GetMapping("/show-bookstocks") //----->SHOWS STOCK OF BOOKS
	public List<Books> showBookStocks(){
		List<Books> booklist = new ArrayList<Books>();
		String url = basicUrl+backendServiceId+"/books";
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<String> requestEntity = new HttpEntity<String>( header);
		
		booklist = restTemplate.exchange(url,
              HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Books>>() { }).getBody();
		return booklist;
	}
	
	@GetMapping("/allorders")//-----> SHOWS ALL ORDER REQUESTS
	public List<Orders> showAllOrders(){
		
		String url = basicUrl+backendServiceId+"/orders";
		
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<String> requestEntity = new HttpEntity<String>( header);
		List<Orders> orderList = restTemplate.exchange(url,
	              HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Orders>>() { }).getBody();
		orderList=orderList.stream().filter(e->!e.isRequeststatus() && !e.isReturnstatus()).collect(Collectors.toList());
		return orderList;
	}
	
	
	
	@GetMapping("/accept-order/{orderid}")//-------> TO ACCEPT ORDER REQUEST
	public Orders acceptOrder(@PathVariable int orderid) 
	{
		String url = basicUrl+backendServiceId+"/orders/"+orderid;
		
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<String> requestEntity = new HttpEntity<String>( header);
		
		
		Orders order = restTemplate.exchange(url,
	              HttpMethod.GET, requestEntity, new ParameterizedTypeReference<Orders>() { }).getBody();
		order.setRequeststatus(true);
		int bookid = order.getBookid();
		url =basicUrl+backendServiceId+"/books/"+bookid;
		Books book = restTemplate.exchange(url,
	              HttpMethod.GET, requestEntity, new ParameterizedTypeReference<Books>() { }).getBody();
		book.setStock(book.getStock()-1);
        HttpEntity<Books> requestEntity1 = new HttpEntity<>(book, header);
        url = basicUrl+backendServiceId+"/books";
        restTemplate.exchange(url,
				 HttpMethod.PUT, requestEntity1, new ParameterizedTypeReference<Books>() { }).getBody();
        url = basicUrl+backendServiceId+"/orders/";
        HttpEntity<Orders> requestEntity2 = new HttpEntity<>(order, header);
		Orders order1 = restTemplate.exchange(url,
				 HttpMethod.PUT, requestEntity2, new ParameterizedTypeReference<Orders>() { }).getBody();
		Delivery delivery = new Delivery(orderid, order.getUserid(), bookid, false, "order");
		HttpEntity<Delivery> requestEntity3 = new HttpEntity<>(delivery, header);
        url = basicUrl+backendServiceId+"/delivery";
        restTemplate.exchange(url,
				 HttpMethod.POST, requestEntity3, new ParameterizedTypeReference<Delivery>() { }).getBody();
		
		return order1;
	}
	
	@GetMapping("/return-list")//-----> SHOWS BOOK RETURNS
	public List<Orders> returnList(){
		String url = basicUrl+backendServiceId+"/orders";
		
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<String> requestEntity = new HttpEntity<String>( header);
		List<Orders> orderList = restTemplate.exchange(url,
	              HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Orders>>() { }).getBody();
		orderList = orderList.stream().filter(e->e.isReturnstatus()).collect(Collectors.toList());
		return orderList;
	}
	
	
	@GetMapping("/accept-return/{orderid}")// ----> TO ACCEPT BOOK RETURN AND REMOVE THAT RECORD             
	public ResponseEntity<?> acceptReturn(@PathVariable int orderid) {
		String url = basicUrl+backendServiceId+"/orders/"+orderid;
		
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<String> requestEntity = new HttpEntity<String>( header);

		Orders order = restTemplate.exchange(url,
	              HttpMethod.GET, requestEntity, new ParameterizedTypeReference<Orders>() { }).getBody();
		int bookid = order.getBookid();
		url = basicUrl+backendServiceId+"/books/"+bookid;
		Books book = restTemplate.exchange(url,
	              HttpMethod.GET, requestEntity, new ParameterizedTypeReference<Books>() { }).getBody();
		book.setStock(book.getStock()+1);
        HttpEntity<Books> requestEntity1 = new HttpEntity<>(book, header);
        url = basicUrl+backendServiceId+"/books";
        restTemplate.exchange(url,
				 HttpMethod.PUT, requestEntity1, new ParameterizedTypeReference<Books>() { }).getBody();
 
        url = basicUrl+backendServiceId+"/orders/"+orderid;
        try {
			restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, new ParameterizedTypeReference<String>() {
			}).getBody();
			return new ResponseEntity<String>("Successfully Returned",HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			return new ResponseEntity<String>("Not Deliverd",HttpStatus.NOT_FOUND);
		}
	}

}
