package com.example.controller;

import com.example.back.entities.RestaurantTables;
import com.example.back.service.RestaurantTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurant-tables")
public class RestaurantTableController {

    @Autowired
    private RestaurantTableService restaurantTableService;

    @GetMapping
    public List<RestaurantTables> getAllRestaurantTables() {
        return restaurantTableService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantTables> getRestaurantTableById(@PathVariable Integer id) {
        Optional<RestaurantTables> RestaurantTables = restaurantTableService.findById(id);
        return RestaurantTables.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public RestaurantTables createRestaurantTable(@RequestBody RestaurantTables RestaurantTables) {
        return restaurantTableService.save(RestaurantTables);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantTables> updateRestaurantTable(@PathVariable Integer id, @RequestBody RestaurantTables restaurantTableDetails) {
        Optional<RestaurantTables> RestaurantTables = restaurantTableService.findById(id);
        if (RestaurantTables.isPresent()) {
            RestaurantTables updatedRestaurantTable = RestaurantTables.get();
            updatedRestaurantTable.setRestaurantId(restaurantTableDetails.getRestaurantId());
            updatedRestaurantTable.setMaxSeats(restaurantTableDetails.getMaxSeats());
            updatedRestaurantTable.setTableShape(restaurantTableDetails.getTableShape());
            return ResponseEntity.ok(restaurantTableService.save(updatedRestaurantTable));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurantTable(@PathVariable Integer id) {
        restaurantTableService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

