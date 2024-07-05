package com.example.controller;

import com.example.back.entities.Restaurants;
import com.example.back.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public List<Restaurants> getAllRestaurants() {
        return restaurantService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurants> getRestaurantById(@PathVariable Integer id) {
        Optional<Restaurants> Restaurants = restaurantService.findById(id);
        return Restaurants.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Restaurants createRestaurant(@RequestBody Restaurants Restaurants) {
        return restaurantService.save(Restaurants);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurants> updateRestaurant(@PathVariable Integer id, @RequestBody Restaurants restaurantDetails) {
        Optional<Restaurants> Restaurants = restaurantService.findById(id);
        if (Restaurants.isPresent()) {
            Restaurants updatedRestaurant = Restaurants.get();
            updatedRestaurant.setName(restaurantDetails.getName());
            updatedRestaurant.setType(restaurantDetails.getType());
            updatedRestaurant.setAddress(restaurantDetails.getAddress());
            updatedRestaurant.setDescription(restaurantDetails.getDescription());
            updatedRestaurant.setContactPerson(restaurantDetails.getContactPerson());
            updatedRestaurant.setPhoneNumber(restaurantDetails.getPhoneNumber());
            updatedRestaurant.setEmail(restaurantDetails.getEmail());
            return ResponseEntity.ok(restaurantService.save(updatedRestaurant));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Integer id) {
        restaurantService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

