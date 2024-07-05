import { Component } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { RecentReservations } from 'src/app/response/RecentReservations';
import { RestaurantsWithWorkers } from 'src/app/response/RestaurantsWithWorkers';
import { ReservationsService } from 'src/app/service/Reservations/reservations.service';
import { RestaurantService } from 'src/app/service/Restaurant/restaurant.service';
import { ReviewService } from 'src/app/service/Review/review.service';
import { UserService } from 'src/app/service/User/user.service';

@Component({
  selector: 'app-restauran-guest',
  templateUrl: './restauran-guest.component.html',
  styleUrls: ['./restauran-guest.component.css']
})
export class RestauranGuestComponent {

  restaurants: Array<RestaurantsWithWorkers>= [];
  showingRestaurants: Array<RestaurantsWithWorkers>= [];
  searchName: string = '';
  searchAddress: string = '';
  searchType: string = '';
  sortField: string = '';
  sortType: string = 'asc';
  constructor( private restaurantService : RestaurantService,private userService:UserService,private reservationService:ReservationsService ,private reviewService:ReviewService) { }

  async ngOnInit() {  
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

  ariaValueText(current: number, max: number) {
		return `${current} out of ${max} hearts`;
	}
  rating = 3.14;

  
 async getRating(restaurantId:number){
    return await firstValueFrom(this.reviewService.getReviewsForRestaurant(restaurantId)) as number;
    // let sum=0;
    // restaurant.reviews.forEach(review=>{
    //   sum+=review.rating;
    // })
    // return sum/restaurant.reviews.length;
  }
}
