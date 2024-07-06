import { HttpClient, HttpParams } from '@angular/common/http';
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

  getUnprocessedReservations(restaurantId: any) {
    return this.http.get<any[]>(`http://localhost:8080/reservations/unprocessed/${restaurantId.restaurantId}`);
  }

  confirmReservation(request: any){
    return this.http.post<string>(`http://localhost:8080/reservations/confirm`, request,{responseType: 'text' as 'json'});
  }

  rejectReservation(request: any){
    return this.http.post<string>(`http://localhost:8080/reservations/reject`, request,{responseType: 'text' as 'json'});
  }
  getAvailableTables(restaurantId: any, date: Date, numberOfGuests: number) {
    let date1 = new Date(date);
    const reservationDate = date1.toISOString();
    console.log(reservationDate);

    const params = new HttpParams()
      .set('reservationDate', reservationDate)
      .set('numberOfGuests', numberOfGuests.toString());

    return this.http.get<any[]>(`http://localhost:8080/reservations/tables/${restaurantId}`, { params });
  }
  getAllTables(restaurantId: any, date: Date, numberOfGuests: number) {
    let date1 = new Date(date);
    const reservationDate = date1.toISOString();
    console.log(reservationDate);

    const params = new HttpParams()
      .set('reservationDate', reservationDate)
      .set('numberOfGuests', numberOfGuests.toString());

    return this.http.get<any[]>(`http://localhost:8080/reservations/allTables/${restaurantId}`, { params });
  }
}
