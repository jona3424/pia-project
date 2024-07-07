import { Component } from '@angular/core';
import { UserService } from '../../service/User/user.service';
import { firstValueFrom } from 'rxjs';
import { RestaurantService } from 'src/app/service/Restaurant/restaurant.service';
import { Restaurant } from 'src/app/entities/Restaurant';
import { User } from 'src/app/entities/User';

@Component({
  selector: 'app-admin-add-waiter',
  templateUrl: './admin-add-waiter.component.html',
  styleUrls: ['./admin-add-waiter.component.css']
})
export class AdminAddWaiterComponent {
  waiter: any = {
    username: '',
    password: '',
    confirmPassword: '',
    securityQuestion: '',
    securityAnswer: '',
    firstName: '',
    lastName: '',
    gender: '',
    address: '',
    phone: '',
    email: '',
    profilePicture: null,
    creditCard: '',
    type: '',
    role: '',
  };
  restaurantId: number = 0;
  waiters: Array<User> = [];
  restaurants: Array<Restaurant>=[];
  imageError: string | null = null;
  profilePictureInvalid: boolean = false;
  maxFileSize = 2 * 1024 * 1024; // 2MB

  constructor(private userService: UserService, private restaurantService: RestaurantService) {}

  async ngOnInit() {
    this.restaurants = await firstValueFrom(this.restaurantService.getAllRestaurants());
    this.waiters = await firstValueFrom(this.userService.getExsistingUsers('konobar'));
  }

  onFileChange(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];

      // Provera veličine fajla
      if (file.size > this.maxFileSize) {
        this.imageError = 'Image size must not exceed 2MB.';
        this.profilePictureInvalid = true;
        return;
      }

      const img = new Image();
      img.src = URL.createObjectURL(file);

      img.onload = () => {
        const width = img.width;
        const height = img.height;

        if (width < 100 || height < 100) {
          this.imageError = 'Image dimensions must be at least 100x100 pixels.';
          this.profilePictureInvalid = true;
        } else if (width > 300 || height > 300) {
          this.imageError = 'Image dimensions must not exceed 300x300 pixels.';
          this.profilePictureInvalid = true;
        } else {
          this.imageError = null;
          this.profilePictureInvalid = false;

          const reader = new FileReader();
          reader.onload = () => {
            this.waiter.profilePictureBase64 = (reader.result as string).split(',')[1]; // Remove the base64 prefix
          };
          reader.readAsDataURL(file);
        }
      };
    } else {
      this.profilePictureInvalid = false;
      this.waiter.profilePictureBase64 = null; // Ensure profilePicture is null if no file is selected
    }
  }

  async onSubmit() {
    this.waiter.type = 'konobar';
    this.waiter.role = 'konobar';
    let res = await firstValueFrom(this.userService.createWaiter(this.waiter));
    if (res != null) {
      let res1=await firstValueFrom(this.restaurantService.assingWorkerToRestaurant(res.userId, this.restaurantId));
      alert('Waiter added successfully');
      this.waiters = await firstValueFrom(this.userService.getExsistingUsers('konobar'));
    } else {
      alert('Error adding waiter!');
    }
  }
}
