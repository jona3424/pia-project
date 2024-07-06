import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { Restaurant } from 'src/app/entities/Restaurant';
import { Food } from 'src/app/models/food.model';
import { CartService } from 'src/app/service/Cart/cart.service';
import { ReservationsService } from 'src/app/service/Reservations/reservations.service';
import { RestaurantService } from 'src/app/service/Restaurant/restaurant.service';

@Component({
  selector: 'app-restaurant-info',
  templateUrl: './restaurant-info.component.html',
  styleUrls: ['./restaurant-info.component.css']
})
export class RestaurantInfoComponent {
  restaurantDetails: Restaurant = new Restaurant();
  restaurantId: number = 0;
  userId: number = 0;
  reservationMessage: string = "";
  isSuccess: boolean = false;
  foodMenu: Food[] = [];
  cartMessage: string = "";

  constructor(
    private route: ActivatedRoute,
    private restaurantService: RestaurantService,
    private reservationsService: ReservationsService,
    private router: Router,
    private cartService: CartService
  ) { }

  async ngOnInit() {
    const restaurantId = this.route.snapshot.paramMap.get('rId');
    const returnrouter = this.route.snapshot.paramMap.get('return');
    const userId = this.route.snapshot.paramMap.get('data');
    if (restaurantId == null || userId == null || returnrouter == null) {
      this.router.navigate([returnrouter, { data: userId, return: returnrouter, isAdmin: 'false' }]);
    } else {
      this.restaurantId = parseInt(restaurantId);
      this.userId = parseInt(userId);
      this.restaurantDetails = await firstValueFrom(this.restaurantService.getRestaurantById(parseInt(restaurantId))) as Restaurant;
      this.loadMenu(this.restaurantId);
    }
  }

  async makeReservation(reservationForm: any) {
    const reservationDto: any = {
      date: reservationForm.value.date,
      time: reservationForm.value.time,
      numberOfPeople: reservationForm.value.numberOfPeople,
      description: reservationForm.value.description
    };

    try {
      this.reservationMessage = await firstValueFrom(this.reservationsService.makeReservation(this.restaurantId, this.userId, reservationDto)) as string;
      this.isSuccess = this.reservationMessage === "Reservation successful.";
    } catch (error: any) {
      this.reservationMessage = error.error || 'An error occurred. Please try again.';
      this.isSuccess = false;
    }
    setTimeout(() => {
      this.reservationMessage = "";
    }, 3000);
  }

  loadMenu(restaurantId: number): void {
    this.restaurantService.getMenu(restaurantId).subscribe(menu => {
      this.foodMenu = menu.map(food => ({
        ...food,
        image: `data:image/jpeg;base64,${food.image}`
      }));
    });
  }

  addToCart(food: Food, quantity: number): void {
    this.cartService.addToCart(food, quantity);
    this.cartMessage = `${quantity} x ${food.name} added to cart.`;

    setTimeout(() => {
      this.cartMessage = "";
    }, 3000);
  }
}
