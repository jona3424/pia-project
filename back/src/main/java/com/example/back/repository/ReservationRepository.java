package com.example.back.repository;

import com.example.back.entities.Reservations;
import com.example.back.entities.RestaurantTables;
import com.example.back.entities.Restaurants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservations, Integer> {

    List<Reservations> findInLastXDays(Date date, Date date2);

    int countByRestaurantIdAndReservationDateBetween(
            Restaurants restaurant, Date startDate, Date endDate);
    List<Reservations> findByRestaurantIdAndTableIdAndReservationDateBetween(
            Restaurants restaurant, RestaurantTables table, Date startDate, Date endDate);
}

