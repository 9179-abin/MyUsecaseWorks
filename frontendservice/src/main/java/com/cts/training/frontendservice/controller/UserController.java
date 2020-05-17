package com.cts.training.frontendservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.cts.training.frontendservice.dto.Books;
import com.cts.training.frontendservice.dto.Delivery;
import com.cts.training.frontendservice.dto.Login;
import com.cts.training.frontendservice.dto.Orders;
import com.cts.training.frontendservice.dto.UserBooks;
import com.cts.training.frontendservice.dto.Users;
import com.cts.training.frontendservice.service.BookService;
import com.cts.training.frontendservice.service.DeliveryService;
import com.cts.training.frontendservice.service.OrderService;
import com.cts.training.frontendservice.service.UserBookService;
import com.cts.training.frontendservice.service.UserService;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {
	
	
	@Autowired
	BookService bookService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	DeliveryService deliveryService;
	
	@Autowired
	UserBookService userBookService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/getbooks") //------> SHOW ALL AVAILABLE BOOKS
	public List<Books> availableBooks()
	{
		List<Books> booklist = new ArrayList<Books>();
		booklist = bookService.getAllBooks();
		booklist = booklist.stream().filter(e->e.getStock()>0).collect(Collectors.toList());
		return booklist;
	}
	 
	
	@GetMapping("/placeorder/{userid}/{bookid}") //------> TO PLACE BOOK ORDER
	public Orders placeOrders(@PathVariable int userid, @PathVariable int bookid) {
		Orders order = new Orders(1, bookid, userid, false, false);
		orderService.placeOrder(order);
		return order;
	}
	
	@GetMapping("/show-userbooks/{userid}") // -------> SHOWS BOOKS BORROWED BY USER
	public List<UserBooks> showUserBooks(@PathVariable int userid)
	{
		List<UserBooks> userbooks = userBookService.getAllUserBooks();
		userbooks = userbooks.stream().filter(e->e.getUserid()==userid).collect(Collectors.toList());
		return userbooks;
	}
	
	
	
	@GetMapping("/return/{tableid}") //-------> RETURN BOOKS BORROWED BY USER
	public Delivery bookReturn(@PathVariable int tableid) 
	{
		UserBooks book = userBookService.getBookById(tableid); 
		int userid = book.getUserid();
		int bookid = book.getBookid();	
		Delivery delivery = deliveryService.getDeliveryDetails(userid, bookid);
		delivery.setDeliverystatus(false);
		delivery.setDeliverytype("return");
		Delivery delivery1 = deliveryService.updateDelivery(delivery);
		userBookService.deleteUserBook(tableid);
		return delivery1;
	}
	
	@PostMapping("/login")//-----> USER LOGIN
	public ResponseEntity<?> userLogin(@RequestBody Login login) 
	{
		try {
			ResponseEntity<Users> responce = userService.userLogin(login.getUsername(), login.getPassword());
			return responce;
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			return new ResponseEntity<String>("No user found",HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody Users user) {
		try {
			ResponseEntity<?> responce = userService.registerUser(user);
			return responce;
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			return new ResponseEntity<String>("Not Registered",HttpStatus.BAD_REQUEST);
			
		}
	
	}
		

}
