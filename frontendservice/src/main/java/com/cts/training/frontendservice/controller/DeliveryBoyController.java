package com.cts.training.frontendservice.controller;

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

import com.cts.training.frontendservice.dto.Delivery;
import com.cts.training.frontendservice.dto.Orders;
import com.cts.training.frontendservice.dto.UserBooks;
import com.cts.training.frontendservice.service.FrontendService;

@RestController
@RequestMapping("/deliveryboy")
public class DeliveryBoyController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	FrontendService frontEndService;
	
	
	@Value("${service.backendService.serviceId}")
	private String backendServiceId;
	
	public String basicUrl = "http://";
	
	@GetMapping("/pending-tasks") //----> SHOWS ALL PENDING DELIVERIES
	public List<Delivery> getPendingTasks(){
		
		String url = basicUrl+backendServiceId+"/delivery";
		
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<String> requestEntity = new HttpEntity<String>( header);
		List<Delivery> deliveryList = restTemplate.exchange(url,
	              HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Delivery>>() { }).getBody();
		deliveryList= deliveryList.stream().filter(e->!e.isDeliverystatus()).collect(Collectors.toList());
		return deliveryList;
	}
	
	
	@GetMapping("/deliver/{orderid}")// --------> PERFORMS DELIVERY AND RETURN
	public ResponseEntity<?> deliverBooks(@PathVariable int orderid) {
		
		String url =basicUrl+backendServiceId+"/delivery/"+orderid;
		
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<String> requestEntity = new HttpEntity<String>( header);
		Delivery delivery = restTemplate.exchange(url,
	              HttpMethod.GET, requestEntity, new ParameterizedTypeReference<Delivery>() { }).getBody();
		delivery.setDeliverystatus(true);
		url = basicUrl+backendServiceId+"/delivery/";
        HttpEntity<Delivery> requestEntity1 = new HttpEntity<>(delivery, header);
        restTemplate.exchange(url,
				 HttpMethod.PUT, requestEntity1, new ParameterizedTypeReference<Delivery>() { }).getBody();
        if(delivery.getDeliverytype().equals("order")) 
        {
        	UserBooks userbook = new UserBooks(1, delivery.getUserid(), delivery.getBookid());
        	url = basicUrl+backendServiceId+"/userbooks";
        	HttpEntity<UserBooks> requestEntity2 = new HttpEntity<>(userbook, header);
        	try {
				restTemplate.exchange(url, HttpMethod.POST, requestEntity2,
						new ParameterizedTypeReference<UserBooks>() { });
				return new ResponseEntity<String>("Successfully Delivered",HttpStatus.OK);
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
				return new ResponseEntity<String>("Not Deliverd",HttpStatus.NOT_FOUND);
			}
  		
        }
        else {
        	url =basicUrl+backendServiceId+"/orders/"+orderid;
    		Orders order = restTemplate.exchange(url,
    	              HttpMethod.GET, requestEntity, new ParameterizedTypeReference<Orders>() { }).getBody();
    		order.setReturnstatus(true);
    		order.setRequeststatus(false);
    		url = basicUrl+backendServiceId+"/delivery/"+orderid;
    		restTemplate.exchange(url,
    	              HttpMethod.DELETE,requestEntity, new ParameterizedTypeReference<String>() { });
    		
    		HttpEntity<Orders> requestEntity2 = new HttpEntity<>(order, header);
    		url = basicUrl+backendServiceId+"/orders";
    		try {
				restTemplate.exchange(url, HttpMethod.PUT, requestEntity2, new ParameterizedTypeReference<UserBooks>() { });
				return new ResponseEntity<String>("Successfully Delivered",HttpStatus.OK);
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
				return new ResponseEntity<String>("Not Deliverd",HttpStatus.NOT_FOUND);
			}
    		
        }
	}

}
