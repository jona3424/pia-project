package com.example.back.repository;

import com.example.back.dto.AverageReservationsPerDayDTO;
import com.example.back.dto.WeiterDay;
import com.example.back.dto.WorkerGuests;
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
    List<Reservations> findActiveReservationsWithUsers(Users userId);
    List<Reservations> findInactiveReservationsWithUsers(Users userId);

    @Query("SELECT r FROM Reservations r WHERE r.status = :status AND r.restaurantId.restaurantId = :restaurantId ORDER BY r.reservationDate ASC")
    List<Reservations> findByStatusAndRestaurantId(@Param("status") String status, @Param("restaurantId") int restaurantId);

    @Query("SELECT COUNT(r) FROM Reservations r WHERE r.restaurantId = :restaurant AND r.reservationDate BETWEEN :startDate AND :endDate AND r.status NOT IN :ignoredStatuses")
    int countByRestaurantIdAndReservationDateBetweenAndStatusNotIn(@Param("restaurant") Restaurants restaurant,
                                                                   @Param("startDate") Date startDate,
                                                                   @Param("endDate") Date endDate,
                                                                   @Param("ignoredStatuses") List<String> ignoredStatuses);

    @Query("SELECT r.tableId.tableId FROM Reservations r WHERE r.restaurantId = :restaurant AND r.reservationDate BETWEEN :startDate AND :endDate AND r.status NOT IN :ignoredStatuses")
    List<Integer> findUnavailableTableIds(@Param("restaurant") Restaurants restaurant,
                                          @Param("startDate") Date startDate,
                                          @Param("endDate") Date endDate,
                                          @Param("ignoredStatuses") List<String> ignoredStatuses);

    List<Reservations> findByWaiterIdAndStatus(Users waiterId, String status);



    @Query(value = "WITH Days(Day,Number) AS ( SELECT 'Monday',0 UNION SELECT 'Tuesday',0 UNION SELECT 'Wednesday',0 UNION SELECT 'Thursday',0 UNION SELECT 'Friday',0 UNION SELECT 'Saturday',0 UNION SELECT 'Sunday',0), \n" +
            "Guests(waiter_id, Day, Number) AS (\n" +
            "SELECT r.waiter_id,DAYNAME(r.reservation_date), SUM(r.number_of_guests) \n" +
            "FROM reservations r\n" +
            "WHERE r.status IN ('Active', 'Done') \n" +
            "    AND r.waiter_id =:weiterId\n" +
            "GROUP BY r.waiter_id,DAYOFWEEK(r.reservation_date))\n" +
            "\n" +
            "SELECT d.Day, GREATEST(d.Number , COALESCE(g.Number,0)) as broj" +
            " FROM Days d LEFT JOIN Guests g ON (d.Day = g.Day);", nativeQuery = true)
    List<WeiterDay> findGuestsPerDayByWaiter(@Param("weiterId") int waiterId);

    @Query(value="SELECT \n" +
            "    r.waiter_id as waiter, \n" +
            "    CONCAT(u.first_name, ' ', u.last_name) as waiterName,\n" +
            "    SUM(r.number_of_guests) AS total_guests\n" +
            "FROM \n" +
            "    reservations r\n" +
            "JOIN \n" +
            "    users u ON r.waiter_id = u.user_id\n" +
            "WHERE \n" +
            "    r.restaurant_id = :restaurantId\n" +
            "    AND r.waiter_id IS NOT NULL\n" +
            "GROUP BY \n" +
            "    r.waiter_id;", nativeQuery = true)
    List<WorkerGuests> findTotalGuestsPerWaiterInRestaurant(@Param("restaurantId") int restaurantId);


    @Query(value = "WITH Days AS ( " +
            "SELECT 'Monday' AS Day UNION ALL " +
            "SELECT 'Tuesday' UNION ALL " +
            "SELECT 'Wednesday' UNION ALL " +
            "SELECT 'Thursday' UNION ALL " +
            "SELECT 'Friday' UNION ALL " +
            "SELECT 'Saturday' UNION ALL " +
            "SELECT 'Sunday' " +
            "), ReservationsPerDay AS ( " +
            "SELECT DAYNAME(reservation_date) AS Day, " +
            "COUNT(*) AS Number " +
            "FROM reservations " +
            "WHERE reservation_date >= DATE_SUB(CURDATE(), INTERVAL 24 MONTH) " +
            "AND status IN ('Active', 'Done') " +
            "AND restaurant_id = :restaurantId " +
            "GROUP BY DAYNAME(reservation_date) " +
            ") " +
            "SELECT d.Day, COALESCE(AVG(r.Number), 0) AS average_reservations " +
            "FROM Days d " +
            "LEFT JOIN ReservationsPerDay r ON d.Day = r.Day " +
            "GROUP BY d.Day " +
            "ORDER BY FIELD(d.Day, 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday')", nativeQuery = true)
    List<AverageReservationsPerDayDTO> findAverageReservationsPerDay(@Param("restaurantId") int restaurantId);

}



