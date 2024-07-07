import { Component, OnInit } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { ReservationsService } from 'src/app/service/Reservations/reservations.service';

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrls: ['./reservations.component.css']
})
export class ReservationsComponent implements OnInit {
  currentReservations: any[] = [];
  archivedReservations: any[] = [];
  userId: number = 0;

  constructor(private reservationService: ReservationsService) { }

  ngOnInit(): void {
    const user = JSON.parse(localStorage.getItem('user')!);
    this.userId = user.userId;
    this.loadCurrentReservations();
    this.loadArchivedReservations();
  }

  async loadCurrentReservations() {
    this.currentReservations=await firstValueFrom(this.reservationService.getActiveReservations(this.userId));
  }
  async cancelReservation(reservation: any) {
    let date = new Date(reservation.reservationDate);
    let currentDate = new Date();
    if (Math.abs(currentDate.getTime() - date.getTime()) < 45 * 60 * 1000) {
      alert("Reservation cannot be canceled. Time difference is less than 45 minutes.");
    } else {
      await firstValueFrom(this.reservationService.cancelReservation(reservation.reservationId));
      this.loadCurrentReservations();
      this.loadArchivedReservations();
    }
  }


  async loadArchivedReservations() {
    this.archivedReservations=await firstValueFrom(this.reservationService.getInactiveReservations(this.userId));

  }
}
