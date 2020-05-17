package com.cts.training.frontendservice.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cts.training.frontendservice.dto.Users;

public interface UserService {
	
	public ResponseEntity<Users> userLogin(String username,String password);
	public List<Users> getAllUsers();
	public ResponseEntity<Users> registerUser(Users user);
	public Users getOneUser(int userid);
}
