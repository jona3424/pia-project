package com.example.back.dto;

import lombok.Data;

@Data
public class CustomTable {
    private Integer tableId;
    private int capacity;
    private boolean available;
    private boolean overCapacity;
    private boolean unavailable;

    public CustomTable(Integer tableId, int capacity, boolean available, boolean overCapacity, boolean unavailable) {
        this.tableId = tableId;
        this.capacity = capacity;
        this.available = available;
        this.overCapacity = overCapacity;
        this.unavailable = unavailable;
    }
}
