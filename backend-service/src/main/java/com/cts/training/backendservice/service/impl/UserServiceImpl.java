package com.cts.training.backendservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.training.backendservice.models.Users;
import com.cts.training.backendservice.repo.UserRepo;
import com.cts.training.backendservice.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepo userrepo;

	@Override
	public Users insert(Users user) {
		Users us = userrepo.save(user);
		return us;
	}

	@Override
	public void remove(int id) {
		userrepo.deleteById(id);
//		ResponseEntity<String> response = new ResponseEntity<String>("Successfully Deleted",HttpStatus.OK);
//		return response;
	}

	@Override
	public List<Users> getAll() {
		return userrepo.findAll();
		
	}

	@Override
	public Users getOne(int id) {
		Optional<Users> usr = userrepo.findById(id);
		Users us = usr.get();
		return us;
	}

	@Override
	public Users getUserByUsernameAndPassword(String username, String password) {
		Users user = userrepo.findByUsernameAndPassword(username, password).get();
		return user;
	}

	@Override
	public Users alter(Users user) {
		Users us = userrepo.save(user);
		return us;
	}

	
}
