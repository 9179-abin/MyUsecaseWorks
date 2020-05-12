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

import com.cts.training.frontendservice.dto.Delivery;
import com.cts.training.frontendservice.service.DeliveryService;
import com.cts.training.frontendservice.service.FrontendService;

@Service
public class DeliveryServiceImpl implements DeliveryService {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	FrontendService frontEndService;
	
	
	@Value("${service.backendService.serviceId}")
	private String backendServiceId;
	
	public String basicUrl = "http://";

	@Override
	public List<Delivery> getAllDelivery() 
	{
		String url = basicUrl+backendServiceId+"/delivery";
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<String> requestEntity = new HttpEntity<String>( header);
		List<Delivery> deliveryList = restTemplate.exchange(url,
	              HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Delivery>>() { }).getBody();
		return deliveryList;
	}

	@Override
	public Delivery getDeliveryById(int orderid)
	{
		String url =basicUrl+backendServiceId+"/delivery/"+orderid;
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<String> requestEntity = new HttpEntity<String>( header);
		Delivery delivery = restTemplate.exchange(url,
	              HttpMethod.GET, requestEntity, new ParameterizedTypeReference<Delivery>() { }).getBody();
		return delivery;
	}

	@Override
	public Delivery updateDelivery(Delivery delivery) {
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<Delivery> requestEntity = new HttpEntity<>(delivery, header);
        String url = basicUrl+backendServiceId+"/delivery/";
		Delivery delivery1 = restTemplate.exchange(url,
				 HttpMethod.PUT, requestEntity, new ParameterizedTypeReference<Delivery>() { }).getBody();
		return delivery1;
		
	}

	@Override
	public Delivery insertDelivery(Delivery delivery) {
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<Delivery> requestEntity3 = new HttpEntity<>(delivery, header);
        String url = basicUrl+backendServiceId+"/delivery";
        Delivery delivery1=restTemplate.exchange(url,
				 HttpMethod.POST, requestEntity3, new ParameterizedTypeReference<Delivery>() { }).getBody();
        return delivery1;
	}

	@Override
	public void deleteDelivery(int orderid) {
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<String> requestEntity = new HttpEntity<String>( header);
		String url = basicUrl+backendServiceId+"/delivery/"+orderid;
		restTemplate.exchange(url,
	              HttpMethod.DELETE,requestEntity, new ParameterizedTypeReference<String>() { });
		
	}

	@Override
	public Delivery getDeliveryDetails(int userid, int bookid) {
		HttpHeaders header = frontEndService.getAuthHeader();
		HttpEntity<String> requestEntity1 = new HttpEntity<String>( header);
		String url = basicUrl+backendServiceId+"/delivery/details/"+userid+"/"+bookid;
		Delivery delivery = restTemplate.exchange(url,
				 HttpMethod.GET,  requestEntity1, new ParameterizedTypeReference<Delivery>() { }).getBody();
		return delivery;
	}

}
