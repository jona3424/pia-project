import { Component } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { RecentReservations } from 'src/app/response/RecentReservations';
import { RestaurantsWithWorkers } from 'src/app/response/RestaurantsWithWorkers';
import { ReservationsService } from 'src/app/service/Reservations/reservations.service';
import { RestaurantService } from 'src/app/service/Restaurant/restaurant.service';
import { UserService } from 'src/app/service/User/user.service';

@Component({
  selector: 'app-home-page-unregistred',
  templateUrl: './home-page-unregistred.component.html',
  styleUrls: ['./home-page-unregistred.component.css']
})
export class HomePageUnregistredComponent {
  totalRestaurants: Number = 0;
  totalGuests: Number = 0;
  reservationsLast24Hours: Number = 0;
  reservationsLast7Days: Number = 0;
  reservationsLast30Days: Number = 0;
  restaurants: Array<RestaurantsWithWorkers>= [];
  showingRestaurants: Array<RestaurantsWithWorkers>= [];
  searchName: string = '';
  searchAddress: string = '';
  searchType: string = '';
  sortField: string = '';
  sortType: string = 'asc';
  constructor( private restaurantService : RestaurantService,private userService:UserService,private reservationService:ReservationsService) { }

  async ngOnInit() {
    let response=await firstValueFrom( this.reservationService.getNumberOfReservations()) as RecentReservations
    this.reservationsLast24Hours = response.Reservations24Hrs==null?0:response.Reservations24Hrs;
    this.reservationsLast7Days = response.Reservations7Days==null?0:response.Reservations7Days;
    this.reservationsLast30Days = response.Reservations30Days==null?0:response.Reservations30Days;
    this.totalRestaurants = await firstValueFrom( this.restaurantService.getNumberOfRestaurants());
    this.totalGuests = await firstValueFrom( this.userService.getNumberOfGuests());
    this.restaurants=await firstValueFrom( this.restaurantService.getAllRestaurantsWithWorkers()) as Array<RestaurantsWithWorkers>
    this.showingRestaurants = [...this.restaurants];

  }



  search(): void {
    this.showingRestaurants = [];
    this.restaurants.forEach(restaurant => {
      if (restaurant.restaurantName.toLowerCase().includes(this.searchName.toLowerCase()) &&
          restaurant.restaurantAddress.toLowerCase().includes(this.searchAddress.toLowerCase()) &&
          restaurant.typeOfRestaurant.toLowerCase().includes(this.searchType.toLowerCase())) {
        this.showingRestaurants.push(restaurant);
      }
    });

  }
  reset(): void {
    this.showingRestaurants = [...this.restaurants];
    this.searchName = '';
    this.searchAddress = '';
    this.searchType = '';
    this.sortField = '';
    this.sortType = 'asc';
  }

  sort(){
    if(this.sortField=='name'){
      if(this.sortType=='asc'){
        this.showingRestaurants.sort((a,b)=>a.restaurantName.localeCompare(b.restaurantName))
      }else{
        this.showingRestaurants.sort((a,b)=>b.restaurantName.localeCompare(a.restaurantName))
      }
    }else if(this.sortField=='address'){
      if(this.sortType=='asc'){
        this.showingRestaurants.sort((a,b)=>a.restaurantAddress.localeCompare(b.restaurantAddress))
      }else{
        this.showingRestaurants.sort((a,b)=>b.restaurantAddress.localeCompare(a.restaurantAddress))
      }
    }else if(this.sortField=='type'){
      if(this.sortType=='asc'){
        this.showingRestaurants.sort((a,b)=>a.typeOfRestaurant.localeCompare(b.typeOfRestaurant))
      }else{
        this.showingRestaurants.sort((a,b)=>b.typeOfRestaurant.localeCompare(a.typeOfRestaurant))
      }
    }
  }
}
