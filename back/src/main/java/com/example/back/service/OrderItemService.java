package com.example.back.service;

import com.example.back.entities.OrderItems;
import com.example.back.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItems> findAll() {
        return orderItemRepository.findAll();
    }

    public Optional<OrderItems> findById(Integer id) {
        return orderItemRepository.findById(id);
    }

    public OrderItems save(OrderItems orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public void deleteById(Integer id) {
        orderItemRepository.deleteById(id);
    }
}

