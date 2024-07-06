package com.example.back.repository;

import com.example.back.entities.Reservations;
import com.example.back.entities.RestaurantTables;
import com.example.back.entities.Restaurants;
import com.example.back.entities.Users;
import jakarta.persistence.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservations, Integer> {

    @Query("SELECT r FROM Reservations r WHERE r.reservationDate BETWEEN :startDate AND :endDate")
    List<Reservations> findInLastXDays(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    int countByRestaurantIdAndReservationDateBetween(
            Restaurants restaurant, Date startDate, Date endDate);
    List<Reservations> findByRestaurantIdAndTableIdAndReservationDateBetween(
            Restaurants restaurant, RestaurantTables table, Date startDate, Date endDate);

    List<Reservations> findActiveReservationsWithUsers(Users userId);
    List<Reservations> findInactiveReservationsWithUsers(Users userId);
}

