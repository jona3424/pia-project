package com.example.back.repository;

import com.example.back.entities.Orders;
import com.example.back.entities.Restaurants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {
    @Query("SELECT o FROM Orders o WHERE (o.status = 'Confirmed' OR o.status = 'Pending') AND o.userId.userId = :userId ORDER BY o.createdAt DESC")
    List<Orders> findActiveOrdersWithUsers(@Param("userId") int userId);

    @Query("SELECT o FROM Orders o WHERE o.status = 'Canceled' OR (o.status = 'Confirmed' AND o.userId.userId = :userId) ORDER BY o.createdAt DESC")
    List<Orders> findArchivedOrdersWithUsers(@Param("userId") int userId);

    List<Orders>findAllByRestaurantId(Restaurants restaurantId);
}

