package com.example.back.service;

import com.example.back.entities.Orders;
import com.example.back.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Orders> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Orders> findById(Integer id) {
        return orderRepository.findById(id);
    }

    public Orders save(Orders order) {
        return orderRepository.save(order);
    }

    public void deleteById(Integer id) {
        orderRepository.deleteById(id);
    }
}

