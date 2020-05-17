package com.cts.training.backendservice.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.training.backendservice.models.Orders;
import com.cts.training.backendservice.service.OrderService;

@RestController
public class OrdersController {
	
	@Autowired
	OrderService orderService;
	
	@GetMapping("/orders")
	public List<Orders> findAll(){
		return orderService.getAll();
	}
	
	@GetMapping("/orders/{orderid}")
	public Orders findOne(@PathVariable int orderid) {
		return orderService.getOne(orderid);
	}
	
	@PostMapping("/orders")
	public Orders save(@RequestBody Orders order) {
		Orders ord = orderService.insert(order);
		return ord;
	}
	
	@DeleteMapping("/orders/{orderid}")
	public void delete(@PathVariable int orderid) {
		orderService.remove(orderid);
//		return response;
	}
	
	@PutMapping("/orders")
	public Orders update(@RequestBody Orders order) {
		Orders ord = orderService.alter(order);
		return ord;
	}

}
