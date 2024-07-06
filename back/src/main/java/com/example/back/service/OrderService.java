package com.example.back.service;

import com.example.back.dto.OrderItemDto;
import com.example.back.entities.MenuItems;
import com.example.back.entities.OrderItems;
import com.example.back.entities.Orders;
import com.example.back.repository.OrderItemRepository;
import com.example.back.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

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

    public void saveOrder(Orders order, List<OrderItemDto> orderItems) {
        Orders savedOrder = orderRepository.saveAndFlush(order);
        List<OrderItems> orderItemsList =  new ArrayList<>();
        for (OrderItemDto itemDto : orderItems) {
            OrderItems orderItem = new OrderItems();
            orderItem.setOrderId(savedOrder);
            orderItem.setMenuItemId(new MenuItems(itemDto.getMenuItemId()));
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setPrice(itemDto.getPrice());
            orderItemsList.add(orderItem);
        }
        orderItemRepository.saveAllAndFlush(orderItemsList);
    }
}

