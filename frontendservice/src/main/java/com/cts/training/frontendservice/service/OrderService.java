package com.cts.training.frontendservice.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cts.training.frontendservice.dto.Orders;

public interface OrderService {
	
	public List<Orders> getAllOrders();
	public Orders getOrderById(int orderid);
	public Orders placeOrder(Orders order);
	public Orders updateOrder(Orders order);
	public void deleteOrder(int orderid);

}
