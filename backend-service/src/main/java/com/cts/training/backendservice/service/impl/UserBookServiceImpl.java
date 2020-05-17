package com.cts.training.backendservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cts.training.backendservice.models.UserBooks;
import com.cts.training.backendservice.repo.UserBookRepo;
import com.cts.training.backendservice.service.UserBookService;

@Service
public class UserBookServiceImpl implements UserBookService {
	
	@Autowired
	UserBookRepo userbookrepo;

	@Override
	public UserBooks insert(UserBooks userbook) {
		
		UserBooks usb= userbookrepo.save(userbook);
		return usb;
		
	}

	@Override
	public UserBooks getOne(int id) {
		Optional<UserBooks> usb  = userbookrepo.findById(id);
		UserBooks userBook = usb.get();
		return userBook;
	}

	@Override
	public List<UserBooks> getAll() {
		return userbookrepo.findAll();
	}

	@Override
	public UserBooks alter(UserBooks userbook) {
		UserBooks usb= userbookrepo.save(userbook);
		return usb;
	}

	@Override
	public void remove(int id) {
		userbookrepo.deleteById(id);
		ResponseEntity<String> response = new ResponseEntity<String>("Successfully Deleted",HttpStatus.OK);
//		return response;
	}

}
