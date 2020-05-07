package com.cts.training.backendservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.training.backendservice.models.Books;
import com.cts.training.backendservice.repo.BookRepo;

@RestController
public class BookController {
	
	@Autowired 
	BookRepo bookrepo;
	

	
	@GetMapping("/books")
	public List<Books> findAll(){
		return bookrepo.findAll();
	}
	
	@GetMapping("/books/{id}")
	public Books findOne(@PathVariable int id) {
		Optional<Books> book = bookrepo.findById(id);
		Books b = book.get();
		return b;
	}
	
	@PostMapping("/books")
	public Books save(@RequestBody Books book) {
		Books b = bookrepo.save(book);
		return b;
	}
	
	@DeleteMapping("/books/{id}")
	public void delete(@PathVariable int id) {
		bookrepo.deleteById(id);
	}
	
	@PutMapping("/books")
	public Books update(@RequestBody Books book) {
		Books b = bookrepo.save(book);
		return b;
	}
	


}
