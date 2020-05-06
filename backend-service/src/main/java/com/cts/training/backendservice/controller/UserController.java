package com.cts.training.backendservice.controller;

//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

//import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.training.backendservice.models.Books;
import com.cts.training.backendservice.models.Delivery;
import com.cts.training.backendservice.models.Login;
import com.cts.training.backendservice.models.Orders;
import com.cts.training.backendservice.models.UserBooks;
import com.cts.training.backendservice.models.Users;
import com.cts.training.backendservice.service.BookService;
import com.cts.training.backendservice.service.DeliveryService;
import com.cts.training.backendservice.service.OrderService;
import com.cts.training.backendservice.service.UserBookService;
import com.cts.training.backendservice.service.UserService;

@RestController
public class UserController {
	
	
	@Autowired
	UserService userservice;
	
	@Autowired
	UserBookService userbookservice;
	
	@Autowired
	BookService bookservice;
	
	@Autowired
	OrderService orderservice;
	
	@Autowired
	DeliveryService deliveryservice;
	
	@GetMapping("/users/available-books")
	public List<Books> availableBooks(){
		List<Books> books = bookservice.getAll();
		books=books.stream().filter(e->e.getStock()>0).collect(Collectors.toList());
		return books;
	}
	
	@GetMapping("/users/place-order/{userid}/{bookid}")
	public void placeOrder(@PathVariable int userid,@PathVariable int bookid) {
		
		Orders order = new Orders(1,bookid, userid, false, false);
		orderservice.insert(order);
		
		
	}
	
	@GetMapping("/users/userbooks/{userid}")
	public List<UserBooks> userBooks(@PathVariable int userid){
		List<UserBooks> books = userbookservice.getAll();
		books = books.stream().filter(e->e.getUserid()==userid).collect(Collectors.toList());
		return books;
	}
	
	@GetMapping("/users/return/{tableid}")
	public void returnBooks(@PathVariable int tableid) {
		int userid = userbookservice.getOne(tableid).getUserid();
		int bookid = userbookservice.getOne(tableid).getBookid();
		Delivery delivery = deliveryservice.getByUseridAndBookid(userid, bookid);
		delivery.setDeliverystatus(false);
		delivery.setDeliverytype("return");
		deliveryservice.alter(delivery);
		userbookservice.remove(tableid);
	}
	
	
	@GetMapping("/users")
	public List<Users> findAll() {
		return userservice.getAll();
	}
	
	@GetMapping("/userbooks/{tableid}")
	public UserBooks getUserBook(@PathVariable int tableid) {
		return userbookservice.getOne(tableid);	}
	
	@DeleteMapping("/userbooks/delete/{tableid}")
	public void deleteUserBook(@PathVariable int tableid) {
		userbookservice.remove(tableid);
	}
	
	@GetMapping("/getall-userbooks")
	public List<UserBooks> getUserBooks(){
		return userbookservice.getAll();
	}
	
	@GetMapping("/users/{id}")
	public Users findOne(@PathVariable int id) {
		Users us = userservice.getOne(id);
		return us;
	}
	
	@PostMapping("/users")
	public Users save(@RequestBody Users usr) {
		Users us = userservice.insert(usr);
		return us;
	}
	
	@DeleteMapping("/users/{id}")
	public void delete(@PathVariable int id) {
		userservice.remove(id);
	}
	
	@PutMapping("/users")
	public Users update(@RequestBody Users usr) {
		Users us = userservice.alter(usr);
		return us;
	}
//	@GetMapping("/users/login")
//	public ResponseEntity<?> login(HttpServletRequest request){
//		String credentials =null;
//		String authorization = request.getHeader("Authorization");
//		if (authorization != null && authorization.startsWith("Basic")) {
//			String base64Credentials = authorization.substring("Basic".length()).trim();
//		    byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
//		    credentials = new String(credDecoded, StandardCharsets.UTF_8);
//		}
//		try {
//			Users user = userservice.getUserByUsernameAndPassword(credentials.split(":")[0],credentials.split(":")[1]);
//			
//			return new ResponseEntity<Users>(user,HttpStatus.OK);
//		} catch (Exception e ) {
//			System.out.println(e.getStackTrace());
//			return new ResponseEntity<String>("No user found",HttpStatus.NOT_FOUND);
//		}
//	}
	
	@PostMapping("/users/login")
	public ResponseEntity<?> login(@RequestBody Login login){
		
		try {
			Users user = userservice.getUserByUsernameAndPassword(login.getUsername(),login.getPassword());
			
			return new ResponseEntity<Users>(user,HttpStatus.OK);
		} catch (Exception e ) {
			System.out.println(e.getStackTrace());
			return new ResponseEntity<String>("No user found",HttpStatus.NOT_FOUND);
		}
	}
	

}
