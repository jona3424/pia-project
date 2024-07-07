package com.example.back.dto;

import lombok.Data;

import java.time.LocalTime;
@Data
public class AcceptOrderDto {
    String status;
    LocalTime estimatedTime;

    public AcceptOrderDto(String status, String estimatedTime) {
        if (estimatedTime==null)
            this.estimatedTime = null;
        else{
            switch (estimatedTime) {
                case "20-30 minutes" -> this.estimatedTime = LocalTime.of(0, 30);
                case "30-40 minutes" -> this.estimatedTime = LocalTime.of(0, 40);
                case "50-60 minutes" -> this.estimatedTime = LocalTime.of(0, 50);
                default -> this.estimatedTime = null;
            }
        }
        this.status = status;

    }
}
