package com.example.back.service;

import com.example.back.dto.OrderItemDto;
import com.example.back.entities.*;
import com.example.back.repository.OrderItemRepository;
import com.example.back.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<Orders> findActiveOrdersWithUsers(int userId) {
        List<Orders> orders = orderRepository.findActiveOrdersWithUsers(userId);

        LocalDateTime now = LocalDateTime.now();

        return orders.stream().filter(order -> {
            if ("Pending".equals(order.getStatus())) {
                return true;
            }
            if ("Confirmed".equals(order.getStatus()) && order.getAcceptedAt() != null && order.getEstimatedTime() != null) {
                LocalDateTime acceptedAt = LocalDateTime.ofInstant(order.getAcceptedAt().toInstant(), ZoneId.systemDefault());
                LocalDateTime estimatedDeliveryTime = acceptedAt.plus(order.getEstimatedTime().getHour(), ChronoUnit.HOURS)
                        .plus(order.getEstimatedTime().getMinute(), ChronoUnit.MINUTES);
                return estimatedDeliveryTime.isAfter(now);
            }
            return false;
        }).collect(Collectors.toList());
    }

    public List<Orders> findArchivedOrdersWithUsers(int userId) {
        List<Orders> orders = orderRepository.findArchivedOrdersWithUsers(userId);

        LocalDateTime now = LocalDateTime.now();

        return orders.stream().filter(order -> {
            if ("Canceled".equals(order.getStatus())) {
                return true;
            }
            if ("Confirmed".equals(order.getStatus()) && order.getAcceptedAt() != null && order.getEstimatedTime() != null) {
                LocalDateTime acceptedAt = LocalDateTime.ofInstant(order.getAcceptedAt().toInstant(), ZoneId.systemDefault());
                LocalDateTime estimatedDeliveryTime = acceptedAt.plus(order.getEstimatedTime().getHour(), ChronoUnit.HOURS)
                        .plus(order.getEstimatedTime().getMinute(), ChronoUnit.MINUTES);
                return estimatedDeliveryTime.isBefore(now);
            }
            return false;
        }).collect(Collectors.toList());
    }
    public List<Orders> getAllOrders(Integer restaurantId) {
        return orderRepository.findAllByRestaurantId(new Restaurants(restaurantId));
    }

    public Orders updateOrderStatus(Integer orderId, String status, LocalTime estimatedTime) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        if ("Confirmed".equals(status)) {
            order.setEstimatedTime(estimatedTime);
            order.setAcceptedAt(new Date());
        }
        return orderRepository.save(order);
    }
}

