package com.example.back.repository;

import com.example.back.entities.RestaurantTables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTables, Integer> {
}

