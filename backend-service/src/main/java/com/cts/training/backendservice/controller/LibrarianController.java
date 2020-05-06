package com.cts.training.backendservice.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cts.training.backendservice.models.Books;
import com.cts.training.backendservice.models.Delivery;
import com.cts.training.backendservice.models.Orders;
import com.cts.training.backendservice.service.BookService;
import com.cts.training.backendservice.service.DeliveryService;
import com.cts.training.backendservice.service.OrderService;

@RestController
public class LibrarianController {
	
	@Autowired
	BookService bookservice;
	
	@Autowired
	OrderService orderservice;
	
	@Autowired
	DeliveryService deliveryservice;
	
	@GetMapping("/librarian/book-stock")//to show books stocks
	public List<Books> getAllBooks(){
		return bookservice.getAll();
	}
	
	@GetMapping("/librarian/book-orders")//to show order requests
	public List<Orders> orders(){
		if(orderservice.getAll()!=null) {
			List<Orders> orders = orderservice.getAll();
			orders = orders.stream().filter(e->!e.isRequeststatus() && !e.isReturnstatus()).collect(Collectors.toList());
			return orders;
		}
		return null;
	}
	
	@GetMapping("/librarian/accept/{orderid}")// action for order accept
	public Orders acceptOrder(@PathVariable int orderid) {
		Orders order = orderservice.getOne(orderid);
		order.setRequeststatus(true);
		int bookid = order.getBookid();
		Books book = bookservice.getOne(bookid);
		book.setStock(book.getStock()-1);
		bookservice.alter(book);
		orderservice.alter(order);
		Delivery delivery = new Delivery(orderid,order.getUserid(),bookid,false,"order");
		deliveryservice.insert(delivery);
		return order;
	}
	
	@GetMapping("/librarian/returned")// shows record of book returns
	public List<Orders> returnedBooks(){
		if(orderservice.getAll()!=null) {
			List<Orders> orders = orderservice.getAll();
			orders = orders.stream().filter(e->e.isReturnstatus()).collect(Collectors.toList());
			return orders;
		}
		return null;
	}
	@DeleteMapping("/librarian/accept-return/{orderid}") // action for delete book record
	public void deleteRecord(@PathVariable int orderid) {
		int bookid = orderservice.getOne(orderid).getBookid();
		Books book = bookservice.getOne(bookid);
		book.setStock(book.getStock()+1);
		bookservice.alter(book);
		orderservice.remove(orderid);
	}

}
