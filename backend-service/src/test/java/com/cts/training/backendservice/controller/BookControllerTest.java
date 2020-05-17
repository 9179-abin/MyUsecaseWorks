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

import com.cts.training.backendservice.models.Books;
import com.cts.training.backendservice.service.BookService;

@RunWith(SpringJUnit4ClassRunner.class)
public class BookControllerTest {
	
	@InjectMocks
	private BookController controller;
	
	@Mock
	BookService bookService;

	@Test
	public void testFindAll() {
		List<Books> books = new ArrayList<Books>();
		books.add(new Books(17, "Lost",10));
		when(bookService.getAll()).thenReturn(books);
		List<Books> books1 = controller.findAll();
		assertEquals(books1, books);
		verify(bookService,times(1)).getAll();	
		}
	
	@Test
	public void testFindOne() {
		Books book = new Books(17, "Lost",10);
		when(bookService.getOne(17)).thenReturn(book);
		Books book1 = controller.findOne(17);
		assertEquals(book1, book);
		verify(bookService,times(1)).getOne(17);
	}
	
	@Test
	public void testSave() {
		Books book = new Books(17, "Lost",10);
		when(bookService.insert(book)).thenReturn(book);
		Books book1 = controller.save(book);
		assertEquals(book1, book);
		verify(bookService,times(1)).insert(book);
	}
	
	@Test
	public void testDelete() {
		ResponseEntity<String> response = new ResponseEntity<String>("Successfully Deleted",HttpStatus.OK);
		when(bookService.remove(11)).thenReturn(response);
		ResponseEntity<String> response1 = controller.delete(11);
		assertEquals(response, response1);
		verify(bookService,times(1)).remove(11);
	}
	
	@Test
	public void testUpdate() {
		Books book = new Books(17, "Lost",10);
		when(bookService.alter(book)).thenReturn(book);
		Books book1 = controller.update(book);
		assertEquals(book1, book);
		verify(bookService,times(1)).alter(book);
	}

}
