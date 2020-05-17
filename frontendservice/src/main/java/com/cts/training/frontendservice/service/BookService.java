package com.cts.training.frontendservice.service;

import java.util.List;

import com.cts.training.frontendservice.dto.Books;

public interface BookService {
	
	public List<Books> getAllBooks();
	public Books getOneBook(int bookid);
	public Books updateStock(int bookid, String u_type);

}
