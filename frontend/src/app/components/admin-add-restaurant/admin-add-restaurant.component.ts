import { Component } from '@angular/core';
import { UserService } from '../../service/User/user.service';

@Component({
  selector: 'app-admin-add-restaurant',
  templateUrl: './admin-add-restaurant.component.html',
  styleUrls: ['./admin-add-restaurant.component.css']
})
export class AdminAddRestaurantComponent {
  restaurant = {
    name: '',
    type: '',
    address: '',
    description: ''
  };

  constructor(private adminService: UserService) { }

  onSubmit(): void {
   
  }

  resetForm(): void {
    this.restaurant = {
      name: '',
      type: '',
      address: '',
      description: ''
    };
  }
}
