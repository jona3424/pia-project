package com.example.back.service;

import com.example.back.dto.CustomTable;
import com.example.back.dto.ReservationDto;
import com.example.back.entities.Reservations;
import com.example.back.entities.RestaurantTables;
import com.example.back.entities.Restaurants;
import com.example.back.entities.Users;
import com.example.back.repository.ReservationRepository;
import com.example.back.repository.RestaurantRepository;
import com.example.back.repository.RestaurantTableRepository;
import com.example.back.repository.UserRepository;
import com.example.back.response.RecentReservations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RestaurantRepository restaurantsRepository;
    @Autowired
    private RestaurantTableRepository restaurantTablesRepository;
    @Autowired
    private UserRepository usersRepository;


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

        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Convert LocalDateTime to Date for querying
        Date currentDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

        // Get the date and time 24 hours before now
        LocalDateTime dateBefore24Hours = now.minusHours(24);
        Date dateBefore24HoursDate = Date.from(dateBefore24Hours.atZone(ZoneId.systemDefault()).toInstant());

        recentReservations.setReservations24Hrs(reservationRepository.findInLastXDays(dateBefore24HoursDate, currentDate).size());

        // Get the date and time 7 days before now
        LocalDateTime dateBefore7Days = now.minusDays(7);
        Date dateBefore7DaysDate = Date.from(dateBefore7Days.atZone(ZoneId.systemDefault()).toInstant());

        recentReservations.setReservations7Days(reservationRepository.findInLastXDays(dateBefore7DaysDate, currentDate).size());

        // Get the date and time 30 days before now
        LocalDateTime dateBefore30Days = now.minusDays(30);
        Date dateBefore30DaysDate = Date.from(dateBefore30Days.atZone(ZoneId.systemDefault()).toInstant());

        recentReservations.setReservations30Days(reservationRepository.findInLastXDays(dateBefore30DaysDate, currentDate).size());

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
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date reservationDate = Date.from(instant);

        // Interval 3 sata pre i posle rezervacije
        LocalDateTime startDateTime = localDateTime.minusHours(3);
        LocalDateTime endDateTime = localDateTime.plusHours(3);
        Instant startInstant = startDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Instant endInstant = endDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date startDate = Date.from(startInstant);
        Date endDate = Date.from(endInstant);

        // Brojanje rezervacija unutar intervala, ignorišući određene statuse
        int countReservations = reservationRepository.countByRestaurantIdAndReservationDateBetweenAndStatusNotIn(
                restaurant, startDate, endDate, Arrays.asList("Canceled", "Rejected", "Expired"));

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


    public List<Reservations>getActiveReservationsWithUsers(Integer userId){
        return reservationRepository.findActiveReservationsWithUsers(new Users(userId));
    }
    public List<Reservations> getInactiveReservationsWithUsers(Integer userId){
        return reservationRepository.findInactiveReservationsWithUsers(new Users(userId));
    }

    public List<Reservations> getUnprocessedReservations(int restaurantId) {
        List<Reservations> pending = reservationRepository.findByStatusAndRestaurantId("Pending", restaurantId);
        List<Reservations> responseList = new ArrayList<>();
        for(Reservations reservation : pending) {
            Date currentDate = new Date();
            if(reservation.getReservationDate().before(currentDate)) {
                reservation.setStatus("Expired");
                reservationRepository.saveAndFlush(reservation);
            }
            else {

                responseList.add(reservation);
            }
        }

        return responseList;
    }

    public void confirmReservation(int reservationId, int tableId, int waiterId) {
        Reservations reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservation.setStatus("Active");
        reservation.setTableId(new RestaurantTables(tableId));
        reservation.setWaiterId(usersRepository.findById(waiterId)
                .orElseThrow(() -> new RuntimeException("Waiter not found")));
        reservationRepository.saveAndFlush(reservation);
    }

    public void rejectReservation(int reservationId, String comment) {
        Reservations reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new RuntimeException("Reservation not found"));
        reservation.setStatus("Rejected");
        reservation.setRejectionComment(comment);
        reservationRepository.saveAndFlush(reservation);
    }


    public List<RestaurantTables> getAvailableTables(Integer restaurantId, String reservationDateStr, int numberOfGuests) {
        Restaurants restaurant = restaurantsRepository.findById(restaurantId).orElse(null);
        if (restaurant == null) {
            throw new RuntimeException("Restaurant not found.");
        }

        // Parse the reservationDate string to LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime reservationDate = LocalDateTime.parse(reservationDateStr, formatter);

        // Calculate the start and end time window
        LocalDateTime startDateTime = reservationDate.minusHours(3);
        LocalDateTime endDateTime = reservationDate.plusHours(3);

        // Convert to Date objects
        Instant startInstant = startDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Instant endInstant = endDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date startDate = Date.from(startInstant);
        Date endDate = Date.from(endInstant);

        // Find all tables for the restaurant
        List<RestaurantTables> allTables = restaurantTablesRepository.findByRestaurantIdAndMaxSeatsGreaterThanEqual(restaurant, numberOfGuests);

        // Find unavailable table IDs within the time window
        List<Integer> unavailableTableIds = reservationRepository.findUnavailableTableIds(
                restaurant, startDate, endDate, Arrays.asList("Canceled", "Rejected", "Expired")
        );

        // Filter out unavailable tables
        return allTables.stream()
                .filter(table -> !unavailableTableIds.contains(table.getTableId()))
                .collect(Collectors.toList());
    }


    public List<CustomTable> getAllTables(Integer restaurantId,String reservationDate,Integer numberOfGuests){
        List<RestaurantTables> allTables = restaurantTablesRepository.findByRestaurantId(new Restaurants(restaurantId));
        List<RestaurantTables> availableTables = getAvailableTables(restaurantId, reservationDate, numberOfGuests);

        // Parse the reservationDate string to LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime reservationDateTime = LocalDateTime.parse(reservationDate, formatter);

        // Calculate the start and end time window
        LocalDateTime startDateTime = reservationDateTime.minusHours(3);
        LocalDateTime endDateTime = reservationDateTime.plusHours(3);

        // Convert to Date objects
        Instant startInstant = startDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Instant endInstant = endDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date startDate = Date.from(startInstant);
        Date endDate = Date.from(endInstant);

        // Find unavailable table IDs within the time window
        List<Integer> unavailableTableIds = reservationRepository.findUnavailableTableIds(
                new Restaurants(restaurantId), startDate, endDate, Arrays.asList("Canceled", "Rejected", "Expired")
        );

        return allTables.stream().map(table -> {
            boolean isAvailable = availableTables.stream().anyMatch(t -> t.getTableId().equals(table.getTableId()));
            boolean isOverCapacity = table.getMaxSeats() < numberOfGuests;
            boolean isUnavailable = unavailableTableIds.contains(table.getTableId());

            return new CustomTable(table.getTableId(), table.getMaxSeats(), isAvailable, isOverCapacity, isUnavailable);
        }).toList();
    }
}

