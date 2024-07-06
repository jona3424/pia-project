import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/entities/User';
import { UserService } from 'src/app/service/User/user.service';

@Component({
  selector: 'app-waiter',
  templateUrl: './waiter.component.html',
  styleUrls: ['./waiter.component.css']
})
export class WaiterComponent {
  activeButton: string = 'update-user';
  currentUser: User = new User();
  constructor(private userService: UserService,private router:Router) { }

  ngOnInit(): void {
    let user=localStorage.getItem("user");
    if(user==null){
      this.router.navigate(['/login']);
    }
    else{
      this.currentUser=JSON.parse(user);
      this.router.navigate([`/konobar/${this.activeButton}`,{ data: this.currentUser.userId ,return: `/konobar/${this.activeButton}`,isAdmin:'false'}]);

    }
  }

  selectComponent1(component: string,userId:Number): void {
    if (this.activeButton === component) {
      this.activeButton = '';
      this.router.navigate(['/konobar']);
    } else {
      this.activeButton = component;
      this.router.navigate([`/konobar/${component}`,{ data: userId ,return: `/konobar/${this.activeButton}`,isAdmin:'false'}]);
    }
  }


  logout(): void {
    localStorage.removeItem("user");
    localStorage.removeItem("restaurantId");
    this.router.navigate(['/login']);
  }
}
