package com.example.back.service;

import com.example.back.entities.Reservations;
import com.example.back.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservations> findAll() {
        return reservationRepository.findAll();
    }

    public Optional<Reservations> findById(Integer id) {
        return reservationRepository.findById(id);
    }

    public Reservations save(Reservations reservation) {
        return reservationRepository.save(reservation);
    }

    public void deleteById(Integer id) {
        reservationRepository.deleteById(id);
    }
}

