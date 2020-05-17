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
import com.cts.training.frontendservice.dto.UserBooks;
import com.cts.training.frontendservice.service.BookService;
import com.cts.training.frontendservice.service.DeliveryService;
import com.cts.training.frontendservice.service.OrderService;
import com.cts.training.frontendservice.service.UserBookService;

@RunWith(SpringJUnit4ClassRunner.class)
public class DeliveryBoyControllerTest {
	
	@InjectMocks
	private DeliveryBoyController controller;
		
	@Mock
	DeliveryService deliveryService;
	
	@Mock
	UserBookService userBookService;
	
	@Mock
	OrderService orderService;
	
	@Mock
	BookService bookService;

	@Test
	public void testGetPendingTasks() {
		List<Delivery> delivery = new ArrayList<Delivery>();
		delivery.add(new Delivery(15, 3, 17,"s12", false, "order"));
		delivery.add(new Delivery(17, 2, 19,"s12", false, "return"));
		delivery.add(new Delivery(18, 1, 34,"s12", true, "return"));
		when(deliveryService.getAllDelivery()).thenReturn(delivery);
		delivery = delivery.stream().filter(e->!e.isDeliverystatus()).collect(Collectors.toList());
		List<Delivery> delivery1 = (List<Delivery>) controller.getPendingTasks();
		assertEquals(delivery, delivery1);
		verify(deliveryService,times(1)).getAllDelivery();
	}
	
	@Test
	public void testDeliverBooks() {
//		Delivery delivery = new Delivery(15, 3, 17,"s12", false, "return");// -->if deliveryType = 'return'
		Delivery delivery = new Delivery(15, 3, 17,"s12", false, "order");// ---> if deliveryType = 'order'
		when(deliveryService.getDeliveryById(15)).thenReturn(delivery);
		delivery.setDeliverystatus(true);
		when(deliveryService.updateDelivery(delivery)).thenReturn(delivery);
		Books book = new  Books(17,"Lost", 10);
		when(bookService.getOneBook(17)).thenReturn(book);
		String bookname = book.getBookname();
		if(delivery.getDeliverytype().equals("order")) {
			
			UserBooks userbook = new UserBooks(1, delivery.getUserid(), delivery.getBookid(),bookname);
			when(userBookService.insertBook(userbook)).thenReturn(userbook);
			
			Delivery delivery1 = controller.deliverBooks(15);
			assertEquals(delivery,delivery1);
		}
		else {
			Orders order = new Orders(15, 17, 3, true, false);
			when(orderService.getOrderById(15)).thenReturn(order);
			order.setReturnstatus(true);
			order.setRequeststatus(false);
			when(orderService.updateOrder(order)).thenReturn(order);
			Delivery delivery1 = controller.deliverBooks(15);
			assertEquals(delivery,delivery1);
		}
	}

}
