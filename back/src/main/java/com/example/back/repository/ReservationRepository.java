package com.example.back.repository;

import com.example.back.entities.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservations, Integer> {

    public List<Reservations> findInLastXDays(Date date, Date date2);
}

