package com.cts.training.backendservice.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cts.training.backendservice.models.Orders;
import com.cts.training.backendservice.service.OrderService;


@RunWith(SpringJUnit4ClassRunner.class)
public class OrdersControllerTest {
	
	@InjectMocks
	private OrdersController controller;
	
	@Mock
	OrderService orderService;

	@Test
	public void testFindAll() {
		List<Orders> orders = new ArrayList<Orders>();
		orders.add(new Orders(15, 17, 3, false, false));
		when(orderService.getAll()).thenReturn(orders);
		List<Orders> orders1 = controller.findAll();
		assertEquals(orders, orders1);
		verify(orderService,times(1)).getAll();
	}
	
	@Test
	public void testFindOne() {
		Orders order = new Orders(15, 17, 3, false, false);
		when(orderService.getOne(15)).thenReturn(order);
		Orders order1 = controller.findOne(15);
		assertEquals(order1, order);
		verify(orderService,times(1)).getOne(15);
	}
	
	@Test
	public void testSave() {
		Orders order = new Orders(15, 17, 3, false, false);
		when(orderService.insert(order)).thenReturn(order);
		Orders order1 = controller.save(order);
		assertEquals(order1, order);
		verify(orderService,times(1)).insert(order);
	}
	
	@Test
	public void testUpdate() {
		Orders order = new Orders(15, 17, 3, false, false);
		when(orderService.alter(order)).thenReturn(order);
		Orders order1 = controller.update(order);
		assertEquals(order1, order);
		verify(orderService,times(1)).alter(order);
	}
	
//	@Test
//	public void  testDelete() {
//		ResponseEntity<String> response = new ResponseEntity<String>("Successfully Deleted",HttpStatus.OK);
//		when(orderService.remove(11)).thenReturn(response);
//		ResponseEntity<String> response1 = controller.delete(11);
//		assertEquals(response, response1);
//		verify(orderService,times(1)).remove(11);
//	}
}
