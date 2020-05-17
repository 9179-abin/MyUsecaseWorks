package com.cts.training.backendservice.service;

import java.util.List;


import com.cts.training.backendservice.models.Delivery;




public interface DeliveryService {
	public Delivery insert(Delivery delivery);
	public Delivery getOne(int id);
	public List<Delivery> getAll();
	public Delivery alter(Delivery delivery);
	public Delivery getByUseridAndBookid(int userid, int bookid);
	public void remove(int id);
}
