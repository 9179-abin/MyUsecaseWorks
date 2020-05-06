package com.cts.training.backendservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.training.backendservice.models.Orders;

public interface OrderRepo extends JpaRepository<Orders, Integer> {

}
