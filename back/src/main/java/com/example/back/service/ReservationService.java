package com.example.back.service;

import com.example.back.dto.ReservationDto;
import com.example.back.entities.Reservations;
import com.example.back.entities.RestaurantTables;
import com.example.back.entities.Restaurants;
import com.example.back.entities.Users;
import com.example.back.repository.ReservationRepository;
import com.example.back.repository.RestaurantRepository;
import com.example.back.repository.RestaurantTableRepository;
import com.example.back.response.RecentReservations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RestaurantRepository restaurantsRepository;
    @Autowired
    private RestaurantTableRepository restaurantTablesRepository;


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

    public RecentReservations getNumberOfReservations() {
        RecentReservations recentReservations = new RecentReservations();
        Date currentDate = new Date();

    // Get the date 24 hours before now
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -24);
        Date dateBefore24Hours = cal.getTime();

        recentReservations.setReservations24Hrs( reservationRepository.findInLastXDays(dateBefore24Hours,currentDate).size() );

        // Get the date 7 days before now
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        Date dateBefore7Days = cal.getTime();
        recentReservations.setReservations7Days( reservationRepository.findInLastXDays(dateBefore7Days,currentDate).size());
        // Get the date 30 days before now
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -30);
        Date dateBefore30Days = cal.getTime();
        recentReservations.setReservations30Days( reservationRepository.findInLastXDays(dateBefore30Days,currentDate).size());

        return recentReservations;
    }
    public String makeReservation(int restaurantId, int userId, ReservationDto reservationDto) {

        Restaurants restaurant = restaurantsRepository.findById(restaurantId).orElse(null);
        if (restaurant == null) {
            return "Restaurant not found.";
        }

        boolean restaurantOpen = restaurant.getOpeningTime().isBefore(reservationDto.getTime()) &&
                restaurant.getClosingTime().isAfter(reservationDto.getTime());

        if (!restaurantOpen) {
            return "The restaurant is closed at the selected time.";
        }

        // Provera dostupnosti stola
        List<RestaurantTables> availableTables = restaurantTablesRepository.findByRestaurantIdAndMaxSeatsGreaterThanEqual(
                restaurant, reservationDto.getNumberOfPeople());
        if (availableTables.isEmpty()) {
            return "No available tables for the selected date.";
        }

        LocalDateTime localDateTime = LocalDateTime.of(reservationDto.getDate(), reservationDto.getTime());
        // Convert LocalDateTime to Instant
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        // Convert Instant to java.util.Date
        Date reservationDate = Date.from(instant);

        // Interval 3 sata pre i posle rezervacije
        LocalDateTime startDateTime = localDateTime.minusHours(3);
        LocalDateTime endDateTime = localDateTime.plusHours(3);
        Instant startInstant = startDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Instant endInstant = endDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date startDate = Date.from(startInstant);
        Date endDate = Date.from(endInstant);

        // Brojanje rezervacija unutar intervala
        int countReservations = reservationRepository.countByRestaurantIdAndReservationDateBetween(
                restaurant, startDate, endDate);

        // Ako ima slobodnih stolova, dozvoli rezervaciju
        if (availableTables.size() > countReservations) {
            Reservations reservation = new Reservations();
            reservation.setUserId(new Users(userId));
            reservation.setRestaurantId(restaurant);
            reservation.setReservationDate(reservationDate);
            reservation.setNumberOfGuests(reservationDto.getNumberOfPeople());
            reservation.setSpecialRequest(reservationDto.getDescription());
            reservation.setStatus("Pending");
            reservation.setCreatedAt(new Date());

            reservationRepository.saveAndFlush(reservation);
            return "Reservation successful.";
        }

        return "No available tables for the selected date and time.";
    }
}

