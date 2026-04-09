package com.example.ecommerce.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.ecommerce.model.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
}