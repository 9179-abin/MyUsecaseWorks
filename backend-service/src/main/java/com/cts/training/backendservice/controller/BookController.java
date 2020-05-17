package com.cts.training.backendservice.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.training.backendservice.models.Books;
import com.cts.training.backendservice.service.BookService;

@RestController
public class BookController {

	
	@Autowired
	BookService bookService;
	

	
	@GetMapping("/books")
	public List<Books> findAll(){
		return bookService.getAll();
	}
	
	@GetMapping("/books/{id}")
	public Books findOne(@PathVariable int id) {
		return bookService.getOne(id);
	}
	
	@PostMapping("/books")
	public Books save(@RequestBody Books book) {
		Books b = bookService.insert(book);
		return b;
	}
	
	@DeleteMapping("/books/{id}")
	public ResponseEntity<String> delete(@PathVariable int id) {
		ResponseEntity<String> response=bookService.remove(id);
		return response;
	}
	
	@PutMapping("/books")
	public Books update(@RequestBody Books book) {
		Books b = bookService.alter(book);
		return b;
	}
	


}
