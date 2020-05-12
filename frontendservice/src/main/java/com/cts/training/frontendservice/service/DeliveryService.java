package com.cts.training.frontendservice.service;

import java.util.List;

import com.cts.training.frontendservice.dto.Delivery;

public interface DeliveryService {
	
	public List<Delivery> getAllDelivery();
	public Delivery getDeliveryById(int orderid);
	public Delivery updateDelivery(Delivery delivery);
	public Delivery insertDelivery(Delivery delivery);
	public void deleteDelivery(int orderid);
	public Delivery getDeliveryDetails(int userid,int bookid);

}
