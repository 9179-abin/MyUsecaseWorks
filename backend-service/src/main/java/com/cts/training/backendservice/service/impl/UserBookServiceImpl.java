package com.cts.training.backendservice.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
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
		UserBooks usb  = userbookrepo.findById(id).get();
		return usb;
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
		
	}

}
