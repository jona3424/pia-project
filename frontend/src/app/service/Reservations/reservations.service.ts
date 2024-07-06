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
  
  makeReservation(restaurantId: number,userId:number, reservationForm: any) {
    return this.http.post<string>(`http://localhost:8080/reservations/make-reservation/${restaurantId}/${userId}`, reservationForm, {responseType: 'text' as 'json'});
  }

  getActiveReservations(userId: number) {
    return this.http.get<any[]>(`http://localhost:8080/reservations/active-reservations-with-users/${userId}`);
  }
  getInactiveReservations(userId: number) {
    return this.http.get<any[]>(`http://localhost:8080/reservations/inactive-reservations-with-users/${userId}`);
  }
}
