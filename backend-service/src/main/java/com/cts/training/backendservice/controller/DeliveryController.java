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

import com.cts.training.backendservice.models.Delivery;
import com.cts.training.backendservice.repo.DeliveryRepo;


@RestController
public class DeliveryController {
	
	@Autowired
	DeliveryRepo deliveryrepo;
	
	@GetMapping("/delivery")
	public List<Delivery> findAll(){
		return deliveryrepo.findAll();
	}
	
	@GetMapping("/delivery/{id}")
	public Delivery findOne(@PathVariable int id) {
		Optional<Delivery> dlv =deliveryrepo.findById(id);
		Delivery delivary = dlv.get();
		return delivary;
	}
	
	@PostMapping("/delivery")
	public Delivery save(@RequestBody Delivery delivery) {
		Delivery delivaries = deliveryrepo.save(delivery);
		return delivaries;
	}
	
	@DeleteMapping("/delivery/{id}")
	public void delete(@PathVariable int id) {
		deliveryrepo.deleteById(id);
	}
	
	@PutMapping("/delivery")
	public Delivery update(@RequestBody Delivery delivery) {
		Delivery dlv = deliveryrepo.save(delivery);
		return dlv;
	}
	
	@GetMapping("/delivery/details/{userid}/{bookid}")
	public Delivery getDetails(@PathVariable int userid,@PathVariable int bookid) {
		System.out.println("\n\n"+userid+" "+bookid+"\n\n");
		Delivery delivery = deliveryrepo.findByUseridAndBookid(userid, bookid).get();
		return delivery;
	}

}
