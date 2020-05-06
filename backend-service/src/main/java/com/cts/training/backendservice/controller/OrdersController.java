package com.cts.training.backendservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.training.backendservice.models.Orders;
import com.cts.training.backendservice.repo.OrderRepo;

@RestController
public class OrdersController {
	
	@Autowired
	OrderRepo orderrepo;
	
	@GetMapping("/orders")
	public List<Orders> findAll(){
		return orderrepo.findAll();
	}
	
	@GetMapping("/orders/{id}")
	public Orders findOne(@PathVariable int id) {
		Optional<Orders> order = orderrepo.findById(id);
		Orders ord = order.get();
		return ord;
	}
	
	@PostMapping("/orders")
	public Orders save(@RequestBody Orders order) {
		Orders ord = orderrepo.save(order);
		return ord;
	}
	
	@DeleteMapping("/orders/{id}")
	public void delete(@PathVariable int id) {
		orderrepo.deleteById(id);
	}
	
	@PutMapping("/orders")
	public Orders update(@RequestBody Orders order) {
		Orders ord = orderrepo.save(order);
		return ord;
	}

}
