package com.cts.training.backendservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.training.backendservice.models.Books;
import com.cts.training.backendservice.repo.BookRepo;
import com.cts.training.backendservice.service.BookService;

@Service
public class BookServiceImpl implements BookService{
	
	@Autowired 
	BookRepo bookrepo;

	@Override
	public Books insert(Books book) {
		Books b = bookrepo.save(book);
		return b;
	}

	@Override
	public Books getOne(int id) {
		Optional<Books> book = bookrepo.findById(id);
		Books b = book.get();
		return b;
	}

	@Override
	public List<Books> getAll() {
		return bookrepo.findAll();
	}

	@Override
	public Books alter(Books book) {
		Books b = bookrepo.save(book);
		return b;
	}

	@Override
	public ResponseEntity<String> remove(int id) {
		bookrepo.deleteById(id);
		ResponseEntity<String> response = new ResponseEntity<String>("Successfully Deleted",HttpStatus.OK);
		return response;
		
	}

}
