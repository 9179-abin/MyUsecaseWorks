package com.cts.training.backendservice.service;

import java.util.List;


import com.cts.training.backendservice.models.UserBooks;



public interface UserBookService {
	public UserBooks insert(UserBooks userbook);
	public UserBooks getOne(int id);
	public List<UserBooks> getAll();
	public UserBooks alter(UserBooks userbook);
	public void remove(int id);

}
