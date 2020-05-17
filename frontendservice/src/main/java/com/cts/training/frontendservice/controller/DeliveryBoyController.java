package com.cts.training.frontendservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.training.frontendservice.dto.Delivery;
import com.cts.training.frontendservice.dto.Orders;
import com.cts.training.frontendservice.dto.UserBooks;
import com.cts.training.frontendservice.service.BookService;
import com.cts.training.frontendservice.service.DeliveryService;
import com.cts.training.frontendservice.service.OrderService;
import com.cts.training.frontendservice.service.UserBookService;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/deliveryboy")
public class DeliveryBoyController {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	DeliveryService deliveryService;
	
	@Autowired
	UserBookService userBookService;
	
	@Autowired
	BookService bookService;
	
	
	@GetMapping("/pending-tasks") //----> SHOWS ALL PENDING DELIVERIES
	public List<Delivery> getPendingTasks(){
		List<Delivery> deliveryList = deliveryService.getAllDelivery();
		deliveryList= deliveryList.stream().filter(e->!e.isDeliverystatus()).collect(Collectors.toList());
		return deliveryList;
	}
	
	
	@GetMapping("/deliver/{orderid}")// --------> PERFORMS DELIVERY AND RETURN
	public Delivery deliverBooks(@PathVariable int orderid) 
	{
		Delivery delivery = deliveryService.getDeliveryById(orderid);
		delivery.setDeliverystatus(true);
        Delivery delivery1 = deliveryService.updateDelivery(delivery);
        String bookname = bookService.getOneBook(delivery.getBookid()).getBookname();
        if(delivery.getDeliverytype().equals("order")) 
        {
        	UserBooks userbook = new UserBooks(1, delivery.getUserid(), delivery.getBookid(),bookname);
			userBookService.insertBook(userbook);
			return delivery1;
  		
        }
        else {
    		Orders order = orderService.getOrderById(orderid);
    		order.setReturnstatus(true);
    		order.setRequeststatus(false);
    		deliveryService.deleteDelivery(orderid);
			orderService.updateOrder(order);
			return delivery1;

    		
        }
	}

}
