package com.cts.training.backendservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cts.training.backendservice.models.Delivery;
import com.cts.training.backendservice.models.Orders;
import com.cts.training.backendservice.models.UserBooks;
import com.cts.training.backendservice.service.DeliveryService;
import com.cts.training.backendservice.service.OrderService;
import com.cts.training.backendservice.service.UserBookService;

@RestController
public class DeliveryBoyController {
	
	@Autowired
	DeliveryService deliveryservice;
	
	@Autowired
	UserBookService userbookservice;
	
	@Autowired
	OrderService orderservice;
	
	@GetMapping("/delivery/pending-tasks")
	public List<Delivery> getPendingAll(){
		List<Delivery> delivery = deliveryservice.getAll();
		delivery = delivery.stream().filter(e->!e.isDeliverystatus()).collect(Collectors.toList());
		return delivery;
	}
	@GetMapping("/delivery/deliver/{orderid}")
	public void deliverBook(@PathVariable int orderid) {
		
		Delivery delivery = deliveryservice.getOne(orderid);
		delivery.setDeliverystatus(true);
		deliveryservice.alter(delivery);
		if(delivery.getDeliverytype().equals("order")) {
			UserBooks userbook = new UserBooks(1, delivery.getUserid(), delivery.getBookid());
			userbookservice.insert(userbook);
		}
		else {
			Orders order = orderservice.getOne(orderid);
			order.setReturnstatus(true);
			order.setRequeststatus(false);
			orderservice.alter(order);
			deliveryservice.remove(orderid);
		}
		
		
		
	}

}
