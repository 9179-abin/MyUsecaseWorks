package com.cts.training.frontendservice.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {
	
	@InjectMocks
	private UserController controller;
	
	@Mock
	BookService bookService;
	
	@Mock
	OrderService orderService;
	
	@Mock
	UserBookService userBookService;
	
	@Mock
	DeliveryService deliveryService;
	
	@Mock
	UserService userService;
	
	
	@Test
	public void testPlaceOrder() {
		Orders order =new Orders(1,12,13,false,false);
		when(orderService.placeOrder(order)).thenReturn(order);
		Orders order1 = (Orders) controller.placeOrders(13, 12);
		System.out.println(order1);
		assertEquals(order1.getBookid(),order.getBookid());
	}
	

	@Test
	public void testAvailableBooks() {
		List<Books> books = new ArrayList<Books>();
		books.add(new Books(13,"Cats",5));
		when(bookService.getAllBooks()).thenReturn(books);
		List<Books> book = (List<Books>)controller.availableBooks();
		assertEquals(books, book);
		verify(bookService,times(1)).getAllBooks();
	}
	
	@Test
	public void testShowUserBooks() {
		List<UserBooks> books = new ArrayList<UserBooks>();
		books.add(new UserBooks(11,2,17));
		books.add(new UserBooks(14,3,19));
		
		when(userBookService.getAllUserBooks()).thenReturn(books);
		List<UserBooks> book = (List<UserBooks>)controller.showUserBooks(2);
		books = books.stream().filter(e->e.getUserid()==2).collect(Collectors.toList());
		assertEquals(books, book);
		verify(userBookService,times(1)).getAllUserBooks();
	}
	
	@Test
	public void testBookReturn() {
		UserBooks book = new UserBooks(11,2,17);
		when(userBookService.getBookById(11)).thenReturn(book);
		Delivery delivery = new Delivery(15,2,17,true,"order");
		when(deliveryService.getDeliveryDetails(2, 17)).thenReturn(delivery);
		delivery.setDeliverystatus(false);
		delivery.setDeliverytype("return");	
		when(deliveryService.updateDelivery(delivery)).thenReturn(delivery);
		Delivery delivery1 = controller.bookReturn(11);
		assertEquals(delivery.getDeliverytype(), delivery1.getDeliverytype());
		verify(deliveryService,times(1)).updateDelivery(delivery1);
	}
	
	@Test
	public void testLogin() {
		Users user = new Users(1, "s37", "abin", "abin123", "user");
		ResponseEntity<Users> response = new ResponseEntity<Users>(user,HttpStatus.OK);
		when(userService.userLogin("abin", "abin123")).thenReturn(response);
		Login login = new Login("abin", "abin123");
		Users user1 = (Users) controller.userLogin(login).getBody();
		assertEquals(user.getUserid(), user1.getUserid());
		verify(userService,times(1)).userLogin("abin", "abin123");
	}
	
	@Test
	public void testRegister() {
		Users user = new Users(1, "s37", "abin", "abin123", "user");
		ResponseEntity<Users> response = new ResponseEntity<Users>(user,HttpStatus.OK);
		when(userService.registerUser(user)).thenReturn(response);
		Users user1 = (Users) controller.register(user).getBody();
		assertEquals(user.getUserid(), user1.getUserid());
		verify(userService,times(1)).registerUser(user);
	}
	

}
