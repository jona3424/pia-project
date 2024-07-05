import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RecentReservations } from 'src/app/response/RecentReservations';

@Injectable({
  providedIn: 'root'
})
export class ReservationsService {

  constructor(private http: HttpClient) { }

  getNumberOfReservations() {
    return this.http.get<RecentReservations>('http://localhost:8080/reservations/number-of-reservations');
  }
}
