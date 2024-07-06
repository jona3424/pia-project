package com.example.back.repository;

import com.example.back.entities.RestaurantWorker;
import com.example.back.entities.Restaurants;
import com.example.back.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantWorkerRepository extends JpaRepository<RestaurantWorker, Integer> {

    public List<Users> findUsersByRestaurantId(Restaurants restaurantId);

    public RestaurantWorker findByUserIdAndRestaurantId(Users userId, Restaurants restaurantId);

    public RestaurantWorker findByUserId(Users userId);
}
