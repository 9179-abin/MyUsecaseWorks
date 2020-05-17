package com.cts.training.backendservice.service;

import java.util.List;


import com.cts.training.backendservice.models.Users;

public interface UserService {
	public Users insert(Users user);
	public void remove(int id);
	public List<Users> getAll();
	public Users getOne(int id);
	public Users getUserByUsernameAndPassword(String username, String password);
	public Users alter(Users user);
}
