package com.cts.training.backendservice.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.training.backendservice.models.Users;

public interface UserRepo extends JpaRepository<Users, Integer> {
	public Optional<Users> findByUsernameAndPassword(String username, String password);

}
