package com.cts.training.backendservice.service;

import java.util.List;


import com.cts.training.backendservice.models.Orders;



public interface OrderService {
	public Orders insert(Orders order);
	public Orders getOne(int id);
	public List<Orders> getAll();
	public Orders alter(Orders order);
	public void remove(int id);
	

}
