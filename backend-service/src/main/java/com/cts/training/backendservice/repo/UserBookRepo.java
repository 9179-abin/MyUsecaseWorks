package com.cts.training.backendservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.training.backendservice.models.UserBooks;

public interface UserBookRepo extends JpaRepository<UserBooks, Integer> {

}
