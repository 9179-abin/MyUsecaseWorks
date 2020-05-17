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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cts.training.frontendservice.dto.Books;
import com.cts.training.frontendservice.dto.Delivery;
import com.cts.training.frontendservice.dto.Orders;
import com.cts.training.frontendservice.dto.Users;
import com.cts.training.frontendservice.service.BookService;
import com.cts.training.frontendservice.service.DeliveryService;
import com.cts.training.frontendservice.service.OrderService;
import com.cts.training.frontendservice.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
public class LibrarianControllerTest {
	
	@InjectMocks
	private LibrarianController controller;
	
	@Mock
	BookService bookService;
	
	@Mock
	OrderService orderService;
	
	@Mock
	DeliveryService deliveryService;
	
	@Mock
	UserService userService;

	@Test
	public void testPrintAll() {
		List<Users> users = new ArrayList<Users>();
		users.add(new Users(1, "s37", "abin", "abin123", "user"));
		when(userService.getAllUsers()).thenReturn(users);
		List<Users> user1 =(List<Users>) controller.printAll();
		assertEquals(user1, users);
		verify(userService,times(1)).getAllUsers();
	}
	
	@Test
	public void testShowBookStocks() {
		List<Books> books = new ArrayList<Books>();
		books.add(new Books(12,"Lost", 10));
		when(bookService.getAllBooks()).thenReturn(books);
		List<Books> book1 = (List<Books>) controller.showBookStocks();
		assertEquals(book1, books);
		verify(bookService,times(1)).getAllBooks();
	}
	
	@Test
	public void testShowAllOrders() {
		List<Orders> orders = new ArrayList<Orders>();
		orders.add(new Orders(12, 17, 2, false, false));
		orders.add(new Orders(13, 19, 1, true, true));
		when(orderService.getAllOrders()).thenReturn(orders);
		orders = orders.stream().filter(e->!e.isRequeststatus() && !e.isReturnstatus()).collect(Collectors.toList());
		List<Orders> order1 = (List<Orders>) controller.showAllOrders();
		assertEquals(order1, orders);
		verify(orderService,times(1)).getAllOrders();
	}
	
	@Test
	public void testReturnList() {
		List<Orders> orders = new ArrayList<Orders>();
		orders.add(new Orders(12, 17, 2, false, false));
		orders.add(new Orders(13, 19, 1, false, true));
		when(orderService.getAllOrders()).thenReturn(orders);
		orders = orders.stream().filter(e->e.isReturnstatus()).collect(Collectors.toList());
		List<Orders> order1 = (List<Orders>) controller.returnList();
		assertEquals(order1, orders);
		verify(orderService,times(1)).getAllOrders();
	}
	
	@Test
	public void testAcceptReturn() {
		Orders order = new Orders(12, 17, 2, false, true);
		when(orderService.getOrderById(12)).thenReturn(order);
		when(bookService.updateStock(17, "incr")).thenReturn(null);
		Orders order1 = controller.acceptReturn(12);
		System.out.println(order1);
		assertEquals(order1, order);
		verify(orderService,times(1)).getOrderById(12);
	}
	
	@Test
	public void testAcceptOrder() {
		Orders order = new Orders(12, 17, 2, false, true);
		when(orderService.getOrderById(12)).thenReturn(order);
		int bookid = order.getBookid();
		order.setRequeststatus(true);
		Books book = new Books(12,"Lost", 10);
		when(orderService.updateOrder(order)).thenReturn(order);
		when(bookService.updateStock(bookid, "decr")).thenReturn(book);
		Users user = new Users(12, "s13", "abin", "abin123", "user");
		when(userService.getOneUser(order.getUserid())).thenReturn(user);
		String seatno = user.getSeatno();
		Delivery delivery = new Delivery(order.getBookid(), order.getUserid(), order.getBookid(), seatno, false, "order");
		when(deliveryService.insertDelivery(delivery)).thenReturn(delivery);
		Orders order1 = controller.acceptOrder(12);
		System.out.println(order1);
	}

}
