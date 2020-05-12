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

import com.cts.training.frontendservice.dto.Books;
import com.cts.training.frontendservice.dto.Delivery;
import com.cts.training.frontendservice.dto.Orders;
import com.cts.training.frontendservice.dto.Users;
import com.cts.training.frontendservice.service.BookService;
import com.cts.training.frontendservice.service.DeliveryService;
import com.cts.training.frontendservice.service.OrderService;
import com.cts.training.frontendservice.service.UserService;

@RestController
@RequestMapping("/librarian")
public class LibrarianController {
	
	@Autowired
	BookService bookService;
	
	@Autowired
	DeliveryService deliveryService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/getallusers")//-----> SHOWS ALL USERS
	public List<Users> printAll(){
		return userService.getAllUsers();
	}
	
	
	@GetMapping("/show-bookstocks") //----->SHOWS STOCK OF BOOKS
	public List<Books> showBookStocks(){
		
		return bookService.getAllBooks();
	}
	
	
	
	@GetMapping("/allorders")//-----> SHOWS ALL ORDER REQUESTS
	public List<Orders> showAllOrders(){
		
		List<Orders> orderList = orderService.getAllOrders();
		orderList=orderList.stream().filter(e->!e.isRequeststatus() && !e.isReturnstatus()).collect(Collectors.toList());
		return orderList;
	}
	
	
	
	@GetMapping("/accept-order/{orderid}")//-------> TO ACCEPT ORDER REQUEST
	public Orders acceptOrder(@PathVariable int orderid) 
	{
		Orders order = orderService.getOrderById(orderid);
		order.setRequeststatus(true);
		int bookid = order.getBookid();
		bookService.updateStock(bookid, "decr");
		Orders order1 = orderService.updateOrder(order);
		Delivery delivery = new Delivery(orderid, order.getUserid(), bookid, false, "order");
        deliveryService.insertDelivery(delivery);
		return order1;
	}
	
	@GetMapping("/return-list")//-----> SHOWS BOOK RETURNS
	public List<Orders> returnList()
	{
		List<Orders> orderList = orderService.getAllOrders();
		orderList = orderList.stream().filter(e->e.isReturnstatus()).collect(Collectors.toList());
		return orderList;
	}
	
	
	@GetMapping("/accept-return/{orderid}")// ----> TO ACCEPT BOOK RETURN AND REMOVE THAT RECORD             
	public ResponseEntity<?> acceptReturn(@PathVariable int orderid) {

		Orders order = orderService.getOrderById(orderid);
		int bookid = order.getBookid();
		bookService.updateStock(bookid, "incr");
        try {
			orderService.deleteOrder(orderid);
			return new ResponseEntity<String>("Successfully Returned",HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			return new ResponseEntity<String>("Not Deliverd",HttpStatus.CONFLICT);
		}
	}

}
