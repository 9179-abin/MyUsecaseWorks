package com.cts.training.frontendservice.service;

import java.util.List;

import com.cts.training.frontendservice.dto.UserBooks;

public interface UserBookService {
	
	public List<UserBooks> getAllUserBooks();
	public void deleteUserBook(int tableid);
	public UserBooks insertBook(UserBooks book);
	public UserBooks getBookById(int tableid);

}
