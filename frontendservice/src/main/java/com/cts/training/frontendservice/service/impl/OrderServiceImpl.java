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

import com.cts.training.frontendservice.dto.Orders;
import com.cts.training.frontendservice.service.FrontendService;
import com.cts.training.frontendservice.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	FrontendService frontEndService;
	
	
	@Value("${service.backendService.serviceId}")
	private String backendServiceId;
	
	public String basicUrl = "http://";
	
	@Override
	public List<Orders> getAllOrders()
	{
		String url = basicUrl+backendServiceId+"/orders";
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<String> requestEntity = new HttpEntity<String>( header);
		List<Orders> orderList = restTemplate.exchange(url,
	              HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Orders>>() { }).getBody();
		return orderList;
	}

	@Override
	public Orders getOrderById(int orderid) 
	{
		String url = basicUrl+backendServiceId+"/orders/"+orderid;
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<String> requestEntity = new HttpEntity<String>( header);
		Orders order = restTemplate.exchange(url,
	              HttpMethod.GET, requestEntity, new ParameterizedTypeReference<Orders>() { }).getBody();
		return order;
	}

	@Override
	public Orders placeOrder(Orders order) {
		HttpHeaders requestHeaders = frontEndService.getAuthHeader();
		String url = basicUrl+backendServiceId+"/orders/";
        HttpEntity<Orders> requestEntity = new HttpEntity<>(order, requestHeaders);
		Orders order1 = restTemplate.exchange(url,
				 HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Orders>() { }).getBody();
		return order1;
	}

	@Override
	public Orders updateOrder(Orders order) {
		HttpHeaders header = frontEndService.getAuthHeader();
		String url = basicUrl+backendServiceId+"/orders/";
        HttpEntity<Orders> requestEntity2 = new HttpEntity<>(order, header);
		Orders order1 = restTemplate.exchange(url,
				 HttpMethod.PUT, requestEntity2, new ParameterizedTypeReference<Orders>() { }).getBody();
		return order1;
	}

	@Override
	public void deleteOrder(int orderid) {
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<String> requestEntity = new HttpEntity<String>( header);
		String url = basicUrl+backendServiceId+"/orders/"+orderid;
		restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, new ParameterizedTypeReference<String>() {
		}).getBody();
	}

}
