package com.example.controller;

import com.example.back.entities.Reservations;
import com.example.back.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public List<Reservations> getAllReservations() {
        return reservationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservations> getReservationById(@PathVariable Integer id) {
        Optional<Reservations> Reservations = reservationService.findById(id);
        return Reservations.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Reservations createReservation(@RequestBody Reservations Reservations) {
        return reservationService.save(Reservations);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservations> updateReservation(@PathVariable Integer id, @RequestBody Reservations reservationDetails) {
        Optional<Reservations> Reservations = reservationService.findById(id);
        if (Reservations.isPresent()) {
            Reservations updatedReservation = Reservations.get();
            updatedReservation.setUserId(reservationDetails.getUserId());
            updatedReservation.setRestaurantId(reservationDetails.getRestaurantId());
            updatedReservation.setTableId(reservationDetails.getTableId());
            updatedReservation.setReservationDate(reservationDetails.getReservationDate());
            updatedReservation.setNumberOfGuests(reservationDetails.getNumberOfGuests());
            updatedReservation.setSpecialRequest(reservationDetails.getSpecialRequest());
            updatedReservation.setStatus(reservationDetails.getStatus());
            return ResponseEntity.ok(reservationService.save(updatedReservation));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Integer id) {
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
