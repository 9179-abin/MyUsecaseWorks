package com.cts.training.backendservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.training.backendservice.models.Books;

public interface BookRepo extends JpaRepository<Books, Integer> {

}
