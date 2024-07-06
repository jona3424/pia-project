package com.example.back.dto;

import lombok.Data;

@Data
public class ConfirmReservationRequest {
    private int reservationId;
    private int tableId;
    private int waiterId;
}
