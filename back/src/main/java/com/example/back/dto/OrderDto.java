package com.example.back.dto;

import com.example.back.entities.Restaurants;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {
    private int userId;
    private Restaurants restaurantId;
    private BigDecimal totalAmount;
    private String status;
    private List<OrderItemDto> orderItems;

}
