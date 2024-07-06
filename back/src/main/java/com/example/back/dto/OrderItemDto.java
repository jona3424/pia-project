package com.example.back.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private int menuItemId;
    private int quantity;
    private BigDecimal price;

}
