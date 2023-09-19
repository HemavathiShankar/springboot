package com.example.orderservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.orderservice.entity.Order;
@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

}
