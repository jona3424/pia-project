import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { firstValueFrom, forkJoin } from 'rxjs';
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
export class RestauranGuestComponent implements OnInit {

  restaurants: Array<RestaurantsWithWorkers> = [];
  showingRestaurants: Array<RestaurantsWithWorkers> = [];
  searchName: string = '';
  searchAddress: string = '';
  searchType: string = '';
  sortField: string = '';
  sortType: string = 'asc';
  ratings: { [key: number]: number } = {};
  activeButton: string = '';

  isAdmin: boolean = false;
  userId: any;
  returnLink:any;

  constructor(
    private restaurantService: RestaurantService,
    private userService: UserService,
    private reservationService: ReservationsService,
    private reviewService: ReviewService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  async ngOnInit() {
    this.userId = this.route.snapshot.paramMap.get('data');
    this.returnLink = this.route.snapshot.paramMap.get('return');
    this.isAdmin = this.route.snapshot.paramMap.get('isAdmin') == 'true';
    this.restaurants = await firstValueFrom(this.restaurantService.getAllRestaurantsWithWorkers()) as Array<RestaurantsWithWorkers>;
    this.showingRestaurants = [...this.restaurants];

    const ratingObservables = this.showingRestaurants.map(restaurant =>
      this.reviewService.getReviewsForRestaurant(restaurant.restaurantId)
    );

    forkJoin(ratingObservables).subscribe(ratings => {
      ratings.forEach((rating, index) => {
        this.ratings[this.showingRestaurants[index].restaurantId] = rating;
      });
    });
  }

  search(): void {
    this.showingRestaurants = this.restaurants.filter(restaurant =>
      restaurant.restaurantName.toLowerCase().includes(this.searchName.toLowerCase()) &&
      restaurant.restaurantAddress.toLowerCase().includes(this.searchAddress.toLowerCase()) &&
      restaurant.typeOfRestaurant.toLowerCase().includes(this.searchType.toLowerCase())
    );
  }

  reset(): void {
    this.showingRestaurants = [...this.restaurants];
    this.searchName = '';
    this.searchAddress = '';
    this.searchType = '';
    this.sortField = '';
    this.sortType = 'asc';
  }

  sort() {
    if (this.sortField === 'name') {
      this.showingRestaurants.sort((a, b) => this.sortType === 'asc' ? a.restaurantName.localeCompare(b.restaurantName) : b.restaurantName.localeCompare(a.restaurantName));
    } else if (this.sortField === 'address') {
      this.showingRestaurants.sort((a, b) => this.sortType === 'asc' ? a.restaurantAddress.localeCompare(b.restaurantAddress) : b.restaurantAddress.localeCompare(a.restaurantAddress));
    } else if (this.sortField === 'type') {
      this.showingRestaurants.sort((a, b) => this.sortType === 'asc' ? a.typeOfRestaurant.localeCompare(b.typeOfRestaurant) : b.typeOfRestaurant.localeCompare(a.typeOfRestaurant));
    }
  }

  getRating(restaurantId: number): number {
    return this.ratings[restaurantId] || 0;
  }

  selectComponent(component: string,restaurantId:Number): void {
    if (this.activeButton === (component + restaurantId)) {
      this.activeButton = '';
      this.router.navigate(['/gost/restaurant-guest']);
    } else {
      this.activeButton = (component + restaurantId);
      this.router.navigateByUrl('/gost/restaurant-guest', { skipLocationChange: true }).then(() => {
        this.router.navigate([`/gost/restaurant-guest/${component}`,{ data: this.userId ,return: `/gost/restaurant-guest/`,isAdmin:'false',rId:restaurantId}]);
      });
    }
  }
}
