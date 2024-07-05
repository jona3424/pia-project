package com.example.back.service;

import com.example.back.entities.RestaurantTables;
import com.example.back.repository.RestaurantTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantTableService {

    @Autowired
    private RestaurantTableRepository restaurantTableRepository;

    public List<RestaurantTables> findAll() {
        return restaurantTableRepository.findAll();
    }

    public Optional<RestaurantTables> findById(Integer id) {
        return restaurantTableRepository.findById(id);
    }

    public RestaurantTables save(RestaurantTables restaurantTable) {
        return restaurantTableRepository.save(restaurantTable);
    }

    public void deleteById(Integer id) {
        restaurantTableRepository.deleteById(id);
    }
}

