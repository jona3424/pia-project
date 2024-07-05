import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  constructor(private http: HttpClient) { }

  getReviewsForRestaurant(restaurantId: number){
    return this.http.get<number>(`http://localhost:8080/reviews/average-rating/${restaurantId}`)
  }

}
