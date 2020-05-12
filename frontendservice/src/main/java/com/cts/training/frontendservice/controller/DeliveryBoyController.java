package com.cts.training.frontendservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.training.frontendservice.dto.Delivery;
import com.cts.training.frontendservice.dto.Orders;
import com.cts.training.frontendservice.dto.UserBooks;
import com.cts.training.frontendservice.service.DeliveryService;
import com.cts.training.frontendservice.service.OrderService;
import com.cts.training.frontendservice.service.UserBookService;

@RestController
@RequestMapping("/deliveryboy")
public class DeliveryBoyController {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	DeliveryService deliveryService;
	
	@Autowired
	UserBookService userBookService;
	
	
	@GetMapping("/pending-tasks") //----> SHOWS ALL PENDING DELIVERIES
	public List<Delivery> getPendingTasks(){
		List<Delivery> deliveryList = deliveryService.getAllDelivery();
		deliveryList= deliveryList.stream().filter(e->!e.isDeliverystatus()).collect(Collectors.toList());
		return deliveryList;
	}
	
	
	@GetMapping("/deliver/{orderid}")// --------> PERFORMS DELIVERY AND RETURN
	public ResponseEntity<?> deliverBooks(@PathVariable int orderid) 
	{
		Delivery delivery = deliveryService.getDeliveryById(orderid);
		delivery.setDeliverystatus(true);
        deliveryService.updateDelivery(delivery);
        if(delivery.getDeliverytype().equals("order")) 
        {
        	UserBooks userbook = new UserBooks(1, delivery.getUserid(), delivery.getBookid());
        	try {
				userBookService.insertBook(userbook);
				return new ResponseEntity<String>("Successfully Delivered",HttpStatus.OK);
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
				return new ResponseEntity<String>("Not Deliverd",HttpStatus.NOT_FOUND);
			}
  		
        }
        else {
    		Orders order = orderService.getOrderById(orderid);
    		order.setReturnstatus(true);
    		order.setRequeststatus(false);
    		deliveryService.deleteDelivery(orderid);
    		try {
				orderService.updateOrder(order);
				return new ResponseEntity<String>("Successfully Delivered",HttpStatus.OK);
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
				return new ResponseEntity<String>("Not Deliverd",HttpStatus.NOT_FOUND);
			}
    		
        }
	}

}
