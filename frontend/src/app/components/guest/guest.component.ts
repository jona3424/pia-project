import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/entities/User';
import { UserService } from 'src/app/service/User/user.service';

@Component({
  selector: 'app-guest',
  templateUrl: './guest.component.html',
  styleUrls: ['./guest.component.css']
})
export class GuestComponent {

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
      this.router.navigate([`/gost/${this.activeButton}`,{ data: this.currentUser.userId ,return: `/gost/${this.activeButton}`,isAdmin:'false'}]);

    }
  }

  selectComponent1(component: string,userId:Number): void {
    if (this.activeButton === component) {
      this.activeButton = '';
      this.router.navigate(['/gost']);
    } else {
      this.activeButton = component;
      this.router.navigate([`/gost/${component}`,{ data: userId ,return: `/gost/${this.activeButton}`,isAdmin:'false'}]);
    }
  }
  selectComponent2(component: string): void {
    // if (this.activeButton === component) {
    //   this.activeButton = '';
    //   this.router.navigate(['/admin/registration-requests']);
    // } else {
    //   this.activeButton = component;
    //   this.router.navigate([`admin/registration-requests/${component}`,{ return: '/admin/registration-requests'}]);
    // }
  }


  logout(): void {
    localStorage.removeItem("user");
    this.router.navigate(['/login']);
  }
}
