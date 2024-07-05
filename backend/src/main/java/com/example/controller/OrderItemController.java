package com.example.controller;

import com.example.back.entities.OrderItems;
import com.example.back.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public List<OrderItems> getAllOrderItems() {
        return orderItemService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItems> getOrderItemById(@PathVariable Integer id) {
        Optional<OrderItems> OrderItems = orderItemService.findById(id);
        return OrderItems.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public OrderItems createOrderItem(@RequestBody OrderItems OrderItems) {
        return orderItemService.save(OrderItems);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItems> updateOrderItem(@PathVariable Integer id, @RequestBody OrderItems orderItemDetails) {
        Optional<OrderItems> OrderItems = orderItemService.findById(id);
        if (OrderItems.isPresent()) {
            OrderItems updatedOrderItem = OrderItems.get();
            updatedOrderItem.setOrderId(orderItemDetails.getOrderId());
            updatedOrderItem.setMenuItemId(orderItemDetails.getMenuItemId());
            updatedOrderItem.setQuantity(orderItemDetails.getQuantity());
            updatedOrderItem.setPrice(orderItemDetails.getPrice());
            return ResponseEntity.ok(orderItemService.save(updatedOrderItem));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Integer id) {
        orderItemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

