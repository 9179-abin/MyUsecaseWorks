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

import com.cts.training.backendservice.models.Delivery;
import com.cts.training.backendservice.service.DeliveryService;


@RestController
public class DeliveryController {
	
	@Autowired
	DeliveryService deliveryService;
	
	@GetMapping("/delivery")
	public List<Delivery> findAll(){
		return deliveryService.getAll();
	}
	
	@GetMapping("/delivery/{id}")
	public Delivery findOne(@PathVariable int id) {
		return deliveryService.getOne(id);
	}
	
	@PostMapping("/delivery")
	public Delivery save(@RequestBody Delivery delivery) {
		Delivery delivaries = deliveryService.insert(delivery);
		return delivaries;
	}
	
	@DeleteMapping("/delivery/{id}")
	public void delete(@PathVariable int id) {
		deliveryService.remove(id);;
//		return response;
	}
	
	@PutMapping("/delivery")
	public Delivery update(@RequestBody Delivery delivery) {
		Delivery dlv = deliveryService.alter(delivery);
		return dlv;
	}
	
	@GetMapping("/delivery/details/{userid}/{bookid}")
	public Delivery getDetails(@PathVariable int userid,@PathVariable int bookid) {
		Delivery delivery = deliveryService.getByUseridAndBookid(userid, bookid);
		return delivery;
	}

}
