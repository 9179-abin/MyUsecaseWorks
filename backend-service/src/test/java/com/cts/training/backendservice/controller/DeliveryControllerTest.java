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

import com.cts.training.backendservice.models.Delivery;
import com.cts.training.backendservice.service.DeliveryService;

@RunWith(SpringJUnit4ClassRunner.class)
public class DeliveryControllerTest {
	
	@InjectMocks
	private DeliveryController controller;
	
	@Mock
	DeliveryService deliveryService;

	@Test
	public void testFindAll() {
		List<Delivery> delivery = new ArrayList<Delivery>();
		delivery.add(new Delivery(15, 3, 17,"s12", false, "order"));
		when(deliveryService.getAll()).thenReturn(delivery);
		List<Delivery> delivery1 = controller.findAll();
		assertEquals(delivery, delivery1);
		verify(deliveryService,times(1)).getAll();
		
	}
	
	@Test
	public void testFindOne() {
		Delivery delivery = new Delivery(15, 3, 17,"s12", false, "order");
		when(deliveryService.getOne(15)).thenReturn(delivery);
		Delivery delivery1 = controller.findOne(15);
		assertEquals(delivery1, delivery);
		verify(deliveryService,times(1)).getOne(15);
	}
	
	@Test
	public void testSave() {
		Delivery delivery = new Delivery(15, 3, 17,"s12", false, "order");
		when(deliveryService.insert(delivery)).thenReturn(delivery);
		Delivery delivery1 = controller.save(delivery);
		assertEquals(delivery1, delivery);
		verify(deliveryService,times(1)).insert(delivery);
	}
	
//	@Test
//	public void testDelete() {
//		ResponseEntity<String> response = new ResponseEntity<String>("Successfully Deleted",HttpStatus.OK);
//		when(deliveryService.remove(11)).thenReturn(response);
//		ResponseEntity<String> response1 = controller.delete(11);
//		assertEquals(response, response1);
//		verify(deliveryService,times(1)).remove(11);
//	}
	
	@Test
	public void testUpdate() {
		Delivery delivery = new Delivery(15, 3, 17,"s12", false, "order");
		when(deliveryService.alter(delivery)).thenReturn(delivery);
		Delivery delivery1 = controller.update(delivery);
		assertEquals(delivery1, delivery);
		verify(deliveryService,times(1)).alter(delivery);
	}
	
	@Test
	public void testGetDetails() {
		Delivery delivery = new Delivery(15, 3, 17,"s12", false, "order");
		when(deliveryService.getByUseridAndBookid(3, 17)).thenReturn(delivery);
		Delivery delivery1 = controller.getDetails(3,17);
		assertEquals(delivery1, delivery);
		verify(deliveryService,times(1)).getByUseridAndBookid(3,17);
	}

}
