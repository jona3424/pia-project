package com.example.back.controller;

import com.example.back.dto.AcceptOrderDto;
import com.example.back.dto.OrderDto;
import com.example.back.entities.Orders;
import com.example.back.entities.Restaurants;
import com.example.back.entities.Users;
import com.example.back.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Orders> getAllOrders() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Integer id) {
        Optional<Orders> Orders = orderService.findById(id);
        return Orders.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Orders createOrder(@RequestBody Orders Orders) {
        return orderService.save(Orders);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orders> updateOrder(@PathVariable Integer id, @RequestBody Orders orderDetails) {
        Optional<Orders> Orders = orderService.findById(id);
        if (Orders.isPresent()) {
            Orders updatedOrder = Orders.get();
            updatedOrder.setUserId(orderDetails.getUserId());
            updatedOrder.setRestaurantId(orderDetails.getRestaurantId());
            updatedOrder.setTotalAmount(orderDetails.getTotalAmount());
            updatedOrder.setStatus(orderDetails.getStatus());
            return ResponseEntity.ok(orderService.save(updatedOrder));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/make-order")
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto) {
        try{
            Orders order = new Orders();
            order.setUserId(new Users(orderDto.getUserId()));
            order.setRestaurantId(orderDto.getRestaurantId());
            order.setTotalAmount(orderDto.getTotalAmount());
            order.setStatus(orderDto.getStatus());
            orderService.saveOrder(order, orderDto.getOrderItems());
            return ResponseEntity.ok("Order successfully created.");
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error creating order.");
        }

    }
    @GetMapping("/current-orders/{userId}")
    public ResponseEntity<?> getCurrentOrders(@PathVariable Integer userId) {
        return ResponseEntity.ok(orderService.findActiveOrdersWithUsers(userId));
    }
    @GetMapping("/past-orders/{userId}")
    public ResponseEntity<?> getPastOrders(@PathVariable Integer userId) {
        return ResponseEntity.ok(orderService.findArchivedOrdersWithUsers(userId));
    }

    @GetMapping("/get-restaurant-orders/{restaurantId}")
    public ResponseEntity<?> getRestaurantOrders(@PathVariable Integer restaurantId) {
        return ResponseEntity.ok(orderService.getAllOrders(restaurantId));
    }
    @PostMapping("/accept-order/{orderId}")
    public ResponseEntity<?> acceptOrder(@PathVariable Integer orderId, @RequestBody AcceptOrderDto acceptOrderDto) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId,  acceptOrderDto.getStatus(),acceptOrderDto.getEstimatedTime()));
    }
}

