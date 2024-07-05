package com.example.back.response;

import lombok.Data;

@Data
public class RecentReservations {
   private int Reservations24Hrs;
   private int Reservations7Days;
   private int Reservations30Days;

    public RecentReservations() {
         this.Reservations24Hrs = 0;
         this.Reservations7Days = 0;
         this.Reservations30Days = 0;
    }

}
