package com.example.back.service;

import com.example.back.entities.Reservations;
import com.example.back.repository.ReservationRepository;
import com.example.back.response.RecentReservations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
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
}

