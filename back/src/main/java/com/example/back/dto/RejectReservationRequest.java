package com.example.back.dto;

import lombok.Data;

@Data
public class RejectReservationRequest {
    private int reservationId;
    private String comment;
}
