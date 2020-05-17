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

import com.cts.training.backendservice.models.UserBooks;
import com.cts.training.backendservice.models.Users;
import com.cts.training.backendservice.service.UserBookService;
import com.cts.training.backendservice.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {
	
	@InjectMocks
	private UserController controller;
	
	@Mock
	UserService userService;
	
	@Mock
	UserBookService userBookService;

	@Test
	public void testFindAll() {
		List<Users> user = new ArrayList<Users>();
		user.add(new Users(2, "s13", "abin", "abin123", "user"));
		when(userService.getAll()).thenReturn(user);
		List<Users> user1 = controller.findAll();
		assertEquals(user1, user);
		verify(userService,times(1)).getAll();
	}
	
	@Test
	public void testInsertUserBooks() {
		UserBooks book = new UserBooks(11,2, 17,"Lost");
		when(userBookService.insert(book)).thenReturn(book);
		UserBooks book1 = controller.insertUserBooks(book);
		assertEquals(book1, book);
		verify(userBookService,times(1)).insert(book);
	}
	
	@Test
	public void testGetUserBook() {
		UserBooks book = new UserBooks(11,2, 17,"Lost");
		when(userBookService.getOne(11)).thenReturn(book);
		UserBooks book1 = controller.getUserBook(11);
		assertEquals(book1, book);
		verify(userBookService,times(1)).getOne(11);
	}
	
//	@Test
//	public void testDeleteUserBooks() {
//		ResponseEntity<String> response = new ResponseEntity<String>("Successfully Deleted",HttpStatus.OK);
//		when(userBookService.remove(11)).thenReturn(response);
//		ResponseEntity<String> response1 = controller.deleteUserBook(11);
//		assertEquals(response1,response);
//		verify(userBookService,times(1)).remove(11);
//	}
	@Test
	public void testGetUserBooks() {
		List<UserBooks> books  = new ArrayList<UserBooks>();
		books.add(new UserBooks(11, 2, 34,"Lost"));
		when(userBookService.getAll()).thenReturn(books);
		List<UserBooks> books1 = controller.getUserBooks();
		assertEquals(books1, books);
		verify(userBookService,times(1)).getAll();
	}
	
	@Test
	public void testFindOne() {
		Users user = new Users(1, "s37", "abin", "abin123", "user");
		when(userService.getOne(1)).thenReturn(user);
		Users user1 = controller.findOne(1);
		assertEquals(user1, user);
		verify(userService,times(1)).getOne(1);
	}
	
	@Test
	public void testSave() {
		Users user = new Users(1, "s37", "abin", "abin123", "user");
		when(userService.insert(user)).thenReturn(user);
		Users user1 = controller.save(user);
		assertEquals(user1, user);
		verify(userService,times(1)).insert(user);
	}
	
//	@Test
//	public void testDelete() {
//		ResponseEntity<String> response = new ResponseEntity<String>("Successfully Deleted",HttpStatus.OK);
//		when(userService.remove(2)).thenReturn(response);
//		ResponseEntity<String> response1 = controller.delete(2);
//		assertEquals(response, response1);
//		verify(userService,times(1)).remove(2);
//	}
	
	@Test
	public void testUpdate() {
		Users user = new Users(1, "s37", "abin", "abin123", "user");
		when(userService.alter(user)).thenReturn(user);
		Users user1  = controller.update(user);
		assertEquals(user1, user);
		verify(userService,times(1)).alter(user);
	}
	
	@Test
	public void testUserLogin() {
		Users user = new Users(1, "s37", "abin", "abin123", "user");
		when(userService.getUserByUsernameAndPassword("abin", "abin123")).thenReturn(user);
		Users user1 = (Users) controller.userLogin("abin", "abin123").getBody();
		assertEquals(user1, user);
		verify(userService,times(1)).getUserByUsernameAndPassword("abin", "abin123");
	}
	

}
