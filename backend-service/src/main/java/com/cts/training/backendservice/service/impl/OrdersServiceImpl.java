package com.cts.training.backendservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.training.backendservice.models.Orders;
import com.cts.training.backendservice.repo.OrderRepo;
import com.cts.training.backendservice.service.OrderService;

@Service
public class OrdersServiceImpl implements OrderService {
	
	@Autowired
	OrderRepo orderrepo;

	@Override
	public Orders insert(Orders order) {
		Orders ord = orderrepo.save(order);
		return ord;
	}

	@Override
	public Orders getOne(int id) {
		Optional<Orders> order = orderrepo.findById(id);
		Orders ord = order.get();
		return ord;
	}

	@Override
	public List<Orders> getAll() {
		return orderrepo.findAll();
	}

	@Override
	public Orders alter(Orders order) {
		Orders ord = orderrepo.save(order);
		return ord;
	}

	@Override
	public void remove(int id) {
		orderrepo.deleteById(id);
		
	}

}
