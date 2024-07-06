import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { User } from 'src/app/entities/User';
import { CartService } from 'src/app/service/Cart/cart.service';
import { RestaurantService } from 'src/app/service/Restaurant/restaurant.service';
import { UserService } from 'src/app/service/User/user.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(private servis: UserService,
    private restaurantService: RestaurantService,
    private router: Router,
    private cartService:CartService
  ){}

  username: string = ""
  password: string = ""

  async login(){
    try {
      const res = await firstValueFrom(this.servis.login(this.username, this.password));
      if(res){
        alert("Login successful" 
        + "\n" + "Welcome " + res.firstName+ "!"
        );

        if(res.role.toLocaleLowerCase() == "admin"){
          alert("You are an admin. You need to use different interface.");
        } else {
          localStorage.setItem("user", JSON.stringify(res));

          // Initialize cart for guest user
          if(res.role.toLocaleLowerCase() == "gost") {
            this.cartService.initializeCartForUser(res.userId);
          }
          if(res.role.toLocaleLowerCase() == "konobar") {
            const restaurant = await firstValueFrom(this.restaurantService.getRestaurantByWorkerId(res.userId));
            localStorage.setItem("restaurantId", JSON.stringify(restaurant));
          }
          this.router.navigate(["/" + res.role.toLowerCase()]);
        }

      } else {
        alert("Login failed. Please check your username and password.");
      }
    } catch (err: any) {
      if (err.status === 404 && err.error.message) {
        alert(err.error.message);
      } else {
        alert("An unexpected error occurred. Please try again later.");
      }
    }
  }
}
