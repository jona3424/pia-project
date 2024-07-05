package com.example.controller;

import com.example.back.entities.Orders;
import com.example.back.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}

