package com.example.back.repository;

import com.example.back.entities.MenuItems;
import com.example.back.entities.Restaurants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItems, Integer> {

    List<MenuItems> findByRestaurantId(Restaurants restaurantId);
}
