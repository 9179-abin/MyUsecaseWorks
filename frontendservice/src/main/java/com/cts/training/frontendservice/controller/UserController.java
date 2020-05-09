package com.cts.training.frontendservice.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cts.training.frontendservice.config.FrontendConfiguration;
import com.cts.training.frontendservice.dto.Books;
import com.cts.training.frontendservice.dto.Delivery;
import com.cts.training.frontendservice.dto.Login;
import com.cts.training.frontendservice.dto.Orders;
import com.cts.training.frontendservice.dto.UserBooks;
import com.cts.training.frontendservice.dto.Users;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private EurekaClient eurekaClient;
	
	@Autowired
	FrontendConfiguration configuration;
	
	@Value("${service.backendService.serviceId}")
	private String backendServiceId;
	
	@GetMapping("/show")
	public  String display(HttpServletRequest request){
		
		return request.getHeader("Authorization");
	}
	
	
	@GetMapping("/getbooks") //------> SHOW ALL AVAILABLE BOOKS
	public List<Books> availableBooks()
	{
		List<Books> booklist = new ArrayList<Books>();
		Application application = eurekaClient.getApplication(backendServiceId);
		InstanceInfo instanceInfo = application.getInstances().get(0);
		String url = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/books";
		HttpHeaders header = new HttpHeaders();
		header.setBasicAuth(configuration.getBackendUsername(), configuration.getBackendPassword());
		HttpEntity<String> requestEntity = new HttpEntity<String>( header);
		booklist = restTemplate.exchange(url,
              HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Books>>() { }).getBody();
		booklist = booklist.stream().filter(e->e.getStock()>0).collect(Collectors.toList());
		return booklist;
	}
	 
	
	@GetMapping("/placeorder/{userid}/{bookid}") //------> TO PLACE BOOK ORDER
	public Orders placeOrder(@PathVariable int userid, @PathVariable int bookid) {
		Orders order = new Orders(1, bookid, userid, false, false);
		HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        requestHeaders.setBasicAuth(configuration.getBackendUsername(), configuration.getBackendPassword());
        Application application = eurekaClient.getApplication(backendServiceId);
		InstanceInfo instanceInfo = application.getInstances().get(0);
		String url = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/orders/";
        HttpEntity<Orders> requestEntity = new HttpEntity<>(order, requestHeaders);
		Orders order1 = restTemplate.exchange(url,
				 HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Orders>() { }).getBody();
		return order1;
	}
	
	@GetMapping("/show-userbooks/{userid}") // -------> SHOWS BOOKS BORROWED BY USER
	public List<UserBooks> showUserBooks(@PathVariable int userid)
	{
		Application application = eurekaClient.getApplication(backendServiceId);
		InstanceInfo instanceInfo = application.getInstances().get(0);
		String url = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/getall-userbooks";
		HttpHeaders header = new HttpHeaders();
		header.setBasicAuth(configuration.getBackendUsername(), configuration.getBackendPassword());
		HttpEntity<String> requestEntity = new HttpEntity<String>( header);
		
		List<UserBooks> userbooks = restTemplate.exchange(url,
                HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<UserBooks>>() {
        }).getBody();
		userbooks = userbooks.stream().filter(e->e.getUserid()==userid).collect(Collectors.toList());
		return userbooks;
	}
	
	
	
	@GetMapping("/return/{tableid}") //-------> RETURN BOOKS BORROWED BY USER
	public Delivery bookReturn(@PathVariable int tableid) {
		
		Application application = eurekaClient.getApplication(backendServiceId);
		InstanceInfo instanceInfo = application.getInstances().get(0);
		String url = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/userbooks/"+tableid;
		
		HttpHeaders header = new HttpHeaders();
		header.setBasicAuth(configuration.getBackendUsername(), configuration.getBackendPassword());
		HttpEntity<String> requestEntity1 = new HttpEntity<String>( header);
		
		int userid = restTemplate.exchange(url,
				 HttpMethod.GET,  requestEntity1, new ParameterizedTypeReference<UserBooks>() { }).getBody().getUserid();
		
		int bookid = restTemplate.exchange(url,
				 HttpMethod.GET,  requestEntity1, new ParameterizedTypeReference<UserBooks>() { }).getBody().getBookid();
		String url1 = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/delivery/details/"+userid+"/"+bookid;
		
		Delivery delivery = restTemplate.exchange(url1,
				 HttpMethod.GET,  requestEntity1, new ParameterizedTypeReference<Delivery>() { }).getBody();
		delivery.setDeliverystatus(false);
		delivery.setDeliverytype("return");
		HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        requestHeaders.setBasicAuth(configuration.getBackendUsername(), configuration.getBackendPassword());
        HttpEntity<Delivery> requestEntity = new HttpEntity<>(delivery, requestHeaders);
        String url2 = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/delivery/";
		Delivery delivery2 = restTemplate.exchange(url2,
				 HttpMethod.PUT, requestEntity, new ParameterizedTypeReference<Delivery>() { }).getBody();
		
		String url3 = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/userbooks/delete/"+tableid;
		restTemplate.exchange(url3,
				 HttpMethod.DELETE, requestEntity1, new ParameterizedTypeReference<UserBooks>() {
       });
		
		return delivery2;
	}
	
	@PostMapping("/login")//-----> USER LOGIN
	public ResponseEntity<?> userLogin(@RequestBody Login login) {
		Application application = eurekaClient.getApplication(backendServiceId);
		InstanceInfo instanceInfo = application.getInstances().get(0);
		String url = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/validuser/"+login.getUsername()+"/"+login.getPassword();
		HttpHeaders header = new HttpHeaders();
		header.setBasicAuth(configuration.getBackendUsername(), configuration.getBackendPassword());
		HttpEntity<String> requestEntity = new HttpEntity<String>( header);
		
		try {
			ResponseEntity<?> responce = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
					new ParameterizedTypeReference<Users>() {
					});
			return responce;
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			return new ResponseEntity<String>("No user found",HttpStatus.NOT_FOUND);
		}
	}
		

}
