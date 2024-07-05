package com.example.back.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RestaurantsWithWorkers {
    private Integer restaurantId;
    private String restaurantName;
    private String typeOfRestaurant;
    private String restaurantAddress;
    private List<String> workerNames;

    public RestaurantsWithWorkers() {
        this.workerNames = new ArrayList<>();
    }
}
