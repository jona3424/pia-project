package com.example.back.repository;

import com.example.back.entities.RestaurantTables;
import com.example.back.entities.Restaurants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTables, Integer> {

    public List<RestaurantTables> findByRestaurantIdAndMaxSeatsGreaterThanEqual(Restaurants restaurantId, Integer maxSeats);

    List<RestaurantTables> findByRestaurantId(Restaurants restaurantId);
}

