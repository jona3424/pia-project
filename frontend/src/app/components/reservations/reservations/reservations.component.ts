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

  async loadArchivedReservations() {
    this.archivedReservations=await firstValueFrom(this.reservationService.getInactiveReservations(this.userId));

  }
}
