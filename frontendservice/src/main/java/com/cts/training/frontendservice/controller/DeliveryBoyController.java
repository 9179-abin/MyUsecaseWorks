package com.cts.training.frontendservice.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cts.training.frontendservice.dto.Delivery;
import com.cts.training.frontendservice.dto.Orders;
import com.cts.training.frontendservice.dto.UserBooks;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

@RestController
@RequestMapping("/deliveryboy")
public class DeliveryBoyController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private EurekaClient eurekaClient;
	
	@GetMapping("/pending-tasks") //----> SHOWS ALL PENDING DELIVERIES
	public List<Delivery> getPendingTasks(){
		Application application = eurekaClient.getApplication("backend-service");
		InstanceInfo instanceInfo = application.getInstances().get(0);
		String url = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/delivery";
		List<Delivery> deliveryList = restTemplate.exchange(url,
	              HttpMethod.GET, null, new ParameterizedTypeReference<List<Delivery>>() { }).getBody();
		deliveryList= deliveryList.stream().filter(e->!e.isDeliverystatus()).collect(Collectors.toList());
		return deliveryList;
	}
	
	@GetMapping("/deliver/{orderid}")// --------> PERFORMS DELIVERY AND RETURN
	public ResponseEntity<?> deliverBooks(@PathVariable int orderid) {
		Application application = eurekaClient.getApplication("backend-service");
		InstanceInfo instanceInfo = application.getInstances().get(0);
		String url = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/delivery/"+orderid;
		Delivery delivery = restTemplate.exchange(url,
	              HttpMethod.GET, null, new ParameterizedTypeReference<Delivery>() { }).getBody();
		delivery.setDeliverystatus(true);
		url = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/delivery/";
		HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Delivery> requestEntity = new HttpEntity<>(delivery, requestHeaders);
        restTemplate.exchange(url,
				 HttpMethod.PUT, requestEntity, new ParameterizedTypeReference<Delivery>() { }).getBody();
        if(delivery.getDeliverytype().equals("order")) 
        {
        	UserBooks userbook = new UserBooks(1, delivery.getUserid(), delivery.getBookid());
        	url = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/userbooks";
        	HttpEntity<UserBooks> requestEntity1 = new HttpEntity<>(userbook, requestHeaders);
        	try {
				restTemplate.exchange(url, HttpMethod.POST, requestEntity1,
						new ParameterizedTypeReference<UserBooks>() { });
				return new ResponseEntity<String>("Successfully Delivered",HttpStatus.OK);
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
				return new ResponseEntity<String>("Not Deliverd",HttpStatus.NOT_FOUND);
			}
  		
        }
        else {
        	url = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/orders/"+orderid;
    		Orders order = restTemplate.exchange(url,
    	              HttpMethod.GET, null, new ParameterizedTypeReference<Orders>() { }).getBody();
    		order.setReturnstatus(true);
    		order.setRequeststatus(false);
    		url = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/delivery/"+orderid;
    		restTemplate.exchange(url,
    	              HttpMethod.DELETE,null, new ParameterizedTypeReference<String>() { });
    		
    		HttpEntity<Orders> requestEntity1 = new HttpEntity<>(order, requestHeaders);
    		url = "http://"+instanceInfo.getIPAddr()+":"+instanceInfo.getPort()+"/orders";
    		try {
				restTemplate.exchange(url, HttpMethod.PUT, requestEntity1, new ParameterizedTypeReference<UserBooks>() { });
				return new ResponseEntity<String>("Successfully Delivered",HttpStatus.OK);
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
				return new ResponseEntity<String>("Not Deliverd",HttpStatus.NOT_FOUND);
			}
    		
        }
	}

}
