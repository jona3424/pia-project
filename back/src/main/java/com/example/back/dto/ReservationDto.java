package com.example.back.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
public class ReservationDto {
    private LocalDate date;
    private LocalTime time;
    private int numberOfPeople;
    private String description;

}
