package com.example.back.controller;

import com.example.back.dto.ConfirmReservationRequest;
import com.example.back.dto.CustomTable;
import com.example.back.dto.RejectReservationRequest;
import com.example.back.dto.ReservationDto;
import com.example.back.entities.Reservations;
import com.example.back.entities.RestaurantTables;
import com.example.back.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    @GetMapping("/number-of-reservations")
    public ResponseEntity<?> getNumberOfReservations() {
        return ResponseEntity.ok(reservationService.getNumberOfReservations());
    }

    @PostMapping(path="/make-reservation/{restaurantId}/{userId}")
    public ResponseEntity<?> makeReservation(@PathVariable Integer restaurantId,@PathVariable Integer userId, @RequestBody ReservationDto reservation) {
        String s= reservationService.makeReservation(restaurantId,userId, reservation);
        return ResponseEntity.ok().body(s);
    }
    @GetMapping("/active-reservations-with-users/{userId}")
    public ResponseEntity<?> getActiveReservationsWithUsers(@PathVariable Integer userId) {
        return ResponseEntity.ok().body(reservationService.getActiveReservationsWithUsers(userId));
    }
    @GetMapping("/inactive-reservations-with-users/{userId}")
    public ResponseEntity<?> getInactiveReservationsWithUsers(@PathVariable Integer userId) {
        return ResponseEntity.ok().body(reservationService.getInactiveReservationsWithUsers(userId));
    }

    @GetMapping("/unprocessed/{restaurantId}")
    public List<Reservations> getUnprocessedReservations(@PathVariable int restaurantId) {
        return reservationService.getUnprocessedReservations(restaurantId);
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmReservation(@RequestBody ConfirmReservationRequest request) {
        reservationService.confirmReservation(request.getReservationId(), request.getTableId(), request.getWaiterId());
        return ResponseEntity.ok("Reservation confirmed.");
    }

    @PostMapping("/reject")
    public ResponseEntity<?> rejectReservation(@RequestBody RejectReservationRequest request) {
        reservationService.rejectReservation(request.getReservationId(), request.getComment());
        return ResponseEntity.ok("Reservation rejected.");
    }

    @GetMapping("/allTables/{restaurantId}")
    public ResponseEntity<List<CustomTable>> getAllTables(
            @PathVariable Integer restaurantId,
            @RequestParam String reservationDate,
            @RequestParam int numberOfGuests) {
        List<CustomTable> tables = reservationService.getAllTables(restaurantId, reservationDate, numberOfGuests);
        return ResponseEntity.ok(tables);
    }


    @GetMapping("/waiterReservations/{waiterId}")
    public ResponseEntity<List<Reservations>> getWaiterReservations(@PathVariable Integer waiterId) {
        List<Reservations> reservations = reservationService.getWaiterReservations(waiterId);
        return ResponseEntity.ok(reservations);
    }

    @PostMapping("/reservations/{reservationId}/{status}")
    public ResponseEntity<?> updateReservationStatus(@PathVariable Integer reservationId, @PathVariable String status) {
        String s = reservationService.updateReservationStatus(reservationId, status);
        return ResponseEntity.ok().body(s);
    }

    @GetMapping("/get-waiter-statistics-per-days/{waiterId}")
    public ResponseEntity<?> getWaiterStatisticsPerDay(@PathVariable Integer waiterId) {
        return ResponseEntity.ok().body(reservationService.reservationService(waiterId));

    }

    @GetMapping("/get-total-guests-per-waiter-in-restaurant/{restaurantId}")
    public ResponseEntity<?> getTotalGuestsPerWaiterInRestaurant(@PathVariable Integer restaurantId) {
        return ResponseEntity.ok().body(reservationService.findTotalGuestsPerWaiterInRestaurant(restaurantId));
    }

    @GetMapping("/average-reservations-per-day/{restaurantId}")
    public ResponseEntity<?> findAverageReservationsPerDay(@PathVariable Integer restaurantId) {
        return ResponseEntity.ok().body(reservationService.findAverageReservationsPerDay(restaurantId));
    }

    @PostMapping("/cancel-reservation/{reservationId}")
    public ResponseEntity<?> cancelReservation(@PathVariable Integer reservationId) {
        String s = reservationService.cancelReservation(reservationId);
        return ResponseEntity.ok().body(s);
    }
}
