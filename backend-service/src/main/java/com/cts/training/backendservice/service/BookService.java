package com.cts.training.backendservice.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cts.training.backendservice.models.Books;

public interface BookService {
	public Books insert(Books book);
	public Books getOne(int id);
	public List<Books> getAll();
	public Books alter(Books book);
	public ResponseEntity<String> remove(int id);
}
