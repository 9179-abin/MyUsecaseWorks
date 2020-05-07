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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cts.training.frontendservice.dto.Books;
import com.cts.training.frontendservice.dto.Delivery;
import com.cts.training.frontendservice.dto.Orders;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

@RestController
public class LibrarianController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private EurekaClient eurekaClient;
	
	@GetMapping("/show-bookstocks") //----->SHOWS STOCK OF BOOKS
	public List<Books> showBookStocks(){
		List<Books> booklist = new ArrayList<Books>();
		Application application = eurekaClient.getApplication("backend-service");
		InstanceInfo instanceInfo = application.getInstances().get(0);
		String url = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/books";
		booklist = restTemplate.exchange(url,
              HttpMethod.GET, null, new ParameterizedTypeReference<List<Books>>() { }).getBody();
		return booklist;
	}
	
	@GetMapping("/allorders")//-----> SHOWS ALL ORDER REQUESTS
	public List<Orders> showAllOrders(){
		Application application = eurekaClient.getApplication("backend-service");
		InstanceInfo instanceInfo = application.getInstances().get(0);
		String url = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/orders";
		
		List<Orders> orderList = restTemplate.exchange(url,
	              HttpMethod.GET, null, new ParameterizedTypeReference<List<Orders>>() { }).getBody();
		orderList=orderList.stream().filter(e->!e.isRequeststatus() && !e.isReturnstatus()).collect(Collectors.toList());
		return orderList;
	}
	
	@GetMapping("/accept-order/{orderid}")//-------> TO ACCEPT ORDER REQUEST
	public Orders acceptOrder(@PathVariable int orderid) 
	{
		Application application = eurekaClient.getApplication("backend-service");
		InstanceInfo instanceInfo = application.getInstances().get(0);
		String url = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/orders/"+orderid;
		Orders order = restTemplate.exchange(url,
	              HttpMethod.GET, null, new ParameterizedTypeReference<Orders>() { }).getBody();
		order.setRequeststatus(true);
		int bookid = order.getBookid();
		String url1 = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/books/"+bookid;
		Books book = restTemplate.exchange(url1,
	              HttpMethod.GET, null, new ParameterizedTypeReference<Books>() { }).getBody();
		book.setStock(book.getStock()-1);
		HttpHeaders requestHeaders1 = new HttpHeaders();
        requestHeaders1.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders1.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Books> requestEntity1 = new HttpEntity<>(book, requestHeaders1);
        String url2 = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/books";
        restTemplate.exchange(url2,
				 HttpMethod.PUT, requestEntity1, new ParameterizedTypeReference<Books>() { }).getBody();
        String url3 = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/orders/";
        HttpEntity<Orders> requestEntity2 = new HttpEntity<>(order, requestHeaders1);
		Orders order1 = restTemplate.exchange(url3,
				 HttpMethod.PUT, requestEntity2, new ParameterizedTypeReference<Orders>() { }).getBody();
		Delivery delivery = new Delivery(orderid, order.getUserid(), bookid, false, "order");
		HttpEntity<Delivery> requestEntity3 = new HttpEntity<>(delivery, requestHeaders1);
        String url4 = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/delivery";
        restTemplate.exchange(url4,
				 HttpMethod.POST, requestEntity3, new ParameterizedTypeReference<Delivery>() { }).getBody();
		
		return order1;
	}
	
	@GetMapping("/return-list")//-----> SHOWS BOOK RETURNS
	public List<Orders> returnList(){
		Application application = eurekaClient.getApplication("backend-service");
		InstanceInfo instanceInfo = application.getInstances().get(0);
		String url = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/orders";
		
		List<Orders> orderList = restTemplate.exchange(url,
	              HttpMethod.GET, null, new ParameterizedTypeReference<List<Orders>>() { }).getBody();
		orderList = orderList.stream().filter(e->e.isReturnstatus()).collect(Collectors.toList());
		return orderList;
	}
	
	@GetMapping("/accept-return/{orderid}")// ----> TO ACCEPT BOOK RETURN AND REMOVE THAT RECORD             
	public void acceptReturn(@PathVariable int orderid) {
		Application application = eurekaClient.getApplication("backend-service");
		InstanceInfo instanceInfo = application.getInstances().get(0);
		String url = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/orders/"+orderid;
		Orders order = restTemplate.exchange(url,
	              HttpMethod.GET, null, new ParameterizedTypeReference<Orders>() { }).getBody();
		int bookid = order.getBookid();
		String url1 = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/books/"+bookid;
		Books book = restTemplate.exchange(url1,
	              HttpMethod.GET, null, new ParameterizedTypeReference<Books>() { }).getBody();
		book.setStock(book.getStock()+1);
		HttpHeaders requestHeaders1 = new HttpHeaders();
        requestHeaders1.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders1.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Books> requestEntity1 = new HttpEntity<>(book, requestHeaders1);
        String url2 = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/books";
        restTemplate.exchange(url2,
				 HttpMethod.PUT, requestEntity1, new ParameterizedTypeReference<Books>() { }).getBody();
        
        String url3 = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/orders/"+orderid;
        restTemplate.exchange(url3,
				 HttpMethod.DELETE, null, new ParameterizedTypeReference<Orders>() { }).getBody();
	}

}
