import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Restaurant } from 'src/app/entities/Restaurant';
import { RestaurantsWithWorkers } from 'src/app/response/RestaurantsWithWorkers';

@Injectable({
  providedIn: 'root'
})
export class RestaurantService {

  constructor(private http: HttpClient) { }

  getNumberOfRestaurants(){
    return this.http.get<Number>(`http://localhost:8080/restaurants/number-of-restaurants`)
  }
  getAllRestaurantsWithWorkers(){
    return this.http.get<Array<RestaurantsWithWorkers>>(`http://localhost:8080/restaurants/restaurants-with-workers`)
  }
  getAllRestaurants(){
    return this.http.get<Array<Restaurant>>('http://localhost:8080/restaurants')
  }

  assingWorkerToRestaurant(workerId: number, restaurantId: number){
    return this.http.post<string>(`http://localhost:8080/restaurants/assign-worker/${restaurantId}/${workerId}`,null,{responseType: 'text' as 'json'})
  }
}
