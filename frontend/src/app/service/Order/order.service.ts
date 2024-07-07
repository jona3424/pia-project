import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient) { }

  createOrder(orderDto: any) {
    return this.http.post<string>(`http://localhost:8080/orders/make-order`, orderDto,{responseType: 'text' as 'json'});
  }

  getCurrentDeliveries(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`http://localhost:8080/orders/current-orders/${userId}`);
  }

  getArchivedDeliveries(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`http://localhost:8080/orders/past-orders/${userId}`);
  }

  getOrdersForRestaurant(restaurantId: number) {
    return this.http.get<any[]>(`http://localhost:8080/orders/get-restaurant-orders/${restaurantId}`);
  }
  changeOrderStatus(orderId: number, status: string,estimatedTime:any) {
    console.log("order id",orderId);
    console.log("status",status);
    console.log("estimated time",estimatedTime);
    let data={
      "estimatedTime":estimatedTime,
      "status":status
    }
    return this.http.post(`http://localhost:8080/orders/accept-order/${orderId}`,data);
  }
}
