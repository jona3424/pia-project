package com.example.back.service;

import com.example.back.entities.Restaurants;
import com.example.back.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<Restaurants> findAll() {
        return restaurantRepository.findAll();
    }

    public Optional<Restaurants> findById(Integer id) {
        return restaurantRepository.findById(id);
    }

    public Restaurants save(Restaurants restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public void deleteById(Integer id) {
        restaurantRepository.deleteById(id);
    }
}

