package com.cts.training.backendservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.training.backendservice.models.Delivery;
import com.cts.training.backendservice.repo.DeliveryRepo;
import com.cts.training.backendservice.service.DeliveryService;
@Service
public class DeliveryServiceImpl implements DeliveryService {
	
	@Autowired
	DeliveryRepo deliveryrepo;
	
	@Override
	public Delivery insert(Delivery delivery) {
		Delivery delivaries = deliveryrepo.save(delivery);
		return delivaries;
	}

	@Override
	public Delivery getOne(int id) {
		Optional<Delivery> dlv =deliveryrepo.findById(id);
		Delivery delivary = dlv.get();
		return delivary;
	}

	@Override
	public List<Delivery> getAll() {
		return deliveryrepo.findAll();
	}

	@Override
	public Delivery alter(Delivery delivery) {
		Delivery dlv = deliveryrepo.save(delivery);
		return dlv;
	}

	@Override
	public void remove(int id) {
		deliveryrepo.deleteById(id);
		ResponseEntity<String> response = new ResponseEntity<String>("Successfully Deleted",HttpStatus.OK);
//		return response;
		
	}

	@Override
	public Delivery getByUseridAndBookid(int userid, int bookid) {
		Delivery delivery = deliveryrepo.findByUseridAndBookid(userid, bookid).get();
		return delivery;
	}

}
