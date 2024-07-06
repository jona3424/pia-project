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

  getRestaurantById(restaurantId: number){
    return this.http.get<Restaurant>(`http://localhost:8080/restaurants/${restaurantId}`)
  }

  getMenu(restaurantId: number){
    return this.http.get<Array<any>>(`http://localhost:8080/menu-items/menu-items-for-restaurant/${restaurantId}`)
  }

  getRestaurantByWorkerId(workerId: number){
    return this.http.get<any>(`http://localhost:8080/restaurants/restaurant-for-worker/${workerId}`)
  }
}
